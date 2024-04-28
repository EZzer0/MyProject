// common.js
function handleDelete(id, type, isRealDelete = false) {
    // 根据是否进行真实删除来调整 URL 路径
    var url = '/' + type + (isRealDelete ? '/' + id : '/del/' + id);
    $.ajax({
        type: 'DELETE',
        url: url,
        success: function (data) {
            var msg = data.message;
            console.log(url)
            if (table && typeof table.reload === 'function') {
                // 删除成功后重新加载表格
                table.reload('test', {
                    done: function () {
                        layer.msg(msg, {time: 1000});
                    }
                });
            } else {
                console.error('表格对象或重新加载方法不可用。');
            }
        },
        error: function (xhr) {
            var errorMsg = xhr.responseJSON ? xhr.responseJSON.message : '操作失败';
            layer.msg(errorMsg);
        }
    });
}

// common.js
function handleAction(action, id, type) {
    var titleMap = {
        add: '添加',
        edit: '编辑',
        view: '查看'
    };
    var title = titleMap[action] + type;
    var url = '/' + type + '/cru?action=' + action;
    if (id) {
        url += '&id=' + id;
    }
    layer.open({
        type: 2,
        title: title,
        content: url,
        area: ['800px', '600px']
    });
}


// common.js
function handleSearch(tableId, searchBtnId, searchInputId) {
    document.getElementById(searchBtnId).addEventListener('click', function () {
        var keyword = document.getElementById(searchInputId).value;
        layui.table.reload(tableId, {
            where: {
                search: keyword
            },
            page: {
                curr: 1
            }
        });
    });
}


