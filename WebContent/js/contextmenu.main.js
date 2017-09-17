var menu = new BootstrapMenu(".disk-item", {
    actionsGroups: [
        ["download", "moveTo", "rename"],
        ["moveToSafe"],
        ["remove"]
    ],
    actions: {
        share: {
            name: "����",
            iconClass: "fa-share-alt",
            classNames: "right-click-menu",// �Զ������ʽ
            onClick: function() {
                alert("����");
            },
        },
        download: {
            name: "����",
            iconClass: "fa-cloud-download",
            classNames: "right-click-menu",
            onClick: function() {
                alert("����");
            },
        },
        moveTo: {
            name: "�ƶ���",
            classNames: "right-click-menu",
            onClick: function() {
                alert("�ƶ���");
            },
        },
        rename: {
            name: "������",
            classNames: "right-click-menu",
            onClick: rename,
        },
        moveToSafe: {
            name: "����������",
            iconClass: "fa-shield",
            classNames: "right-click-menu",
            onClick: function() {
                alert("����������");
            },
        },
        remove: {
            name: "ɾ��",
            iconClass: "fa-trash",
            classNames: "right-click-menu",
            onClick: remove,
        }
    }
});
function multiple() {
    var isMultiple = getSelectedItems().length != 1;
    return isMultiple;   
}


/* ��ȡ��ѡ�еĽڵ� */
function getSelectedItems() {
    var selectedItem = $(".selected");
    return selectedItem;
}


/*------------------------------------------------- ���������� -------------------------------------------------*/


function rename() {
    var selectedItem = getSelectedItems();
    if (selectedItem.find(".fileedit").length == 0) {// ��ֹ�������ļ����༭��
        var originalNameTag = selectedItem.find(".file-name");
        originalNameTag.parent().hide();// hide .file-title
        /* ����һ���ļ����༭�� */
        var fileedit = $('<span class="fileedit"><input type="text" /></span>');
        fileedit.find("input").val(originalNameTag.text());
        fileedit.find("input").blur(confirmEditName);// �����ʧȥ����ʱ��ȷ��
        fileedit.find("input").keydown(confirmEditNameByKeyboard);// ���»س�����ȷ��
        fileedit.click(function(e){e.stopPropagation()});// ��ֹð�ݣ���������һ���ĵ����¼�

        fileedit.appendTo(selectedItem.find(".file-head"));
        fileedit.find("input").focus();
    } else {// ����ļ����༭���Ѵ��ڣ�focus
        selectedItem.find(".fileedit input").focus();
    }
}
/* ���»س�����ʹ�����ʧȥ���� */
function confirmEditNameByKeyboard(event) {
    if (event.keyCode == 13) {
        $(this).blur();
    }
}
/* ȷ�����������������ʧȥ����󴥷����¼������� */
function confirmEditName() {
    var inputTag = $(this);
    var itemTag = inputTag.parent().parent().parent();
    var nameTag = inputTag.parent().prev().find(".file-name");
    var originalName = nameTag.text();
    var newName = inputTag.val();


    /* ��Ҫ���������������� */
    var id;
    var localName;
    var localType = null;
    if (itemTag.attr("data-folder-id") != undefined) {
        id = itemTag.attr("data-folder-id");
        localName = newName;
    } else {
        id = itemTag.attr("data-file-id");
        localName = getFilenameWithoutSuffix(newName);
        localType = getSuffix(newName);
    }
    var renameData = {
        id: id,
        localName: localName,
        localType: localType
    };


    if (newName.length ==0) {
        // TO DO �ж��Ƿ�ȫΪ�ո�
        alert("���ֲ���Ϊ��");
    } else if (originalName == newName) {
        // Do nothing
    } else if (checkDuplicateName(itemTag, newName)) {
        alert("�ļ���������ͻ");
    } else if (originalName != newName) {
        $.ajax({
            type: "POST",
            url: "RequestManageServlet?action=rename",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(renameData),
            success: function(result) {
                if (result.isSuccess == true) {
                    nameTag.text(newName);
                    itemTag.find(".file-time").text(getFormattedDateTime(result.ldt_modified));
                    if (localType != null) {
                        var src = getFileIcon(localType);
                        itemTag.find(".thumb img").attr("src", src);
                    }
                } else {
                    alert("������ʧ�ܣ�");
                }
            }
        });
    }
    /* �Ƴ��ļ����༭�� */
    inputTag.parent().remove();

    nameTag.parent().show();
}
/*
 * ����޸ĺ���ļ����Ƿ��뵱ǰ�ļ����µ������ļ���������ͻ
 * return true if ������ͻ��eles return false��
 */
function checkDuplicateName(itemTag, newName) {
    var siblingTags = itemTag.siblings();
    var isDuplicate = false;
    siblingTags.each(function() {
        if ($(this).find(".file-name").text() == newName) {
            isDuplicate = true;
        }
    });
    return isDuplicate;
}

/************************************* ɾ�� *************************************/
function remove() {
    var selectedItem = getSelectedItems();
    selectedItem.each(function() {
        var itemTag = $(this);
        /* ��Ҫ�ύ������ */
        var id;
        var type;
        if (itemTag.attr("data-folder-id") != undefined) {
            id = itemTag.attr("data-folder-id");
            type = "folder";
        } else {
            id = itemTag.attr("data-file-id");
            type = "file";
        }
        var moveData = {
            id: id,
            type: type,
            moveTo: 3
        };

        $.ajax({
            type: "POST",
            url: "RequestManageServlet?action=move",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(moveData),
            success: function(result){
                if (result.isSuccess == true) {
                    /* ����ģ̬���е��ļ��нڵ� */
                    if (type == "folder") {
                        $('.treeNode-info[data-folder-id="' + id + '"]' ).parent().hide();
                    }

                    itemTag.removeClass("disk-item");
                    itemTag.addClass("recycle-item");
                    itemTag.find(".file-time").text("ʣ��7��");
                    itemTag.appendTo($("#recycle_folder"));
                } else {
                    alert("ɾ��ʧ��");
                }
            }
        });
    });
}