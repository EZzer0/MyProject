var common = {
    submitForm: function (action, FieldName, FieldId, baseUrl, successCallback) {
        var thAction = action;
        var id = FieldId;
        var url = thAction === 'edit' ? baseUrl + '/' + id : (thAction === 'add' ? baseUrl : '');
        var method = thAction === 'edit' ? 'PUT' : 'POST';
        var formData = {};
        $('#' + FieldName).serializeArray().forEach(function (item) {
            if (item.name.endsWith('[]')) {
                var name = item.name.slice(0, -2);
                if (!formData[name]) {
                    formData[name] = [];
                }
                formData[name].push(item.value);
            } else {
                formData[item.name] = item.value;
            }
        });

        $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function (res) {
                if (res.code === 0) {
                    parent.layer.msg(res.message, {
                        time: 1000,
                        end: function () {
                            successCallback();
                        }
                    });
                } else {
                    layer.msg(res.message);
                }
            },
            error: function () {
                layer.msg('请求失败');
            }
        });
    }
};
