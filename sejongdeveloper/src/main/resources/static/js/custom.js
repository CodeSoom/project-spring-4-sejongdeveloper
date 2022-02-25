function getElemParam(data) {
    const param = {};

    data.querySelectorAll('input, select').forEach(function (elem) {
        param[elem.name] = elem.value;
    });

    return param;
}

function getElemParams(selector) {
    const params = [];

    document.querySelectorAll(selector).forEach(function (elem) {
        const param = getElemParam(elem);

        params.push(param);
    });

    return params;
}