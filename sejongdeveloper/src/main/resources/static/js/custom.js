function getElemParam(data) {
    const param = {};

    data.querySelectorAll('input, select').forEach(function (elem) {
        param[elem.name] = elem.value;
    });

    return param;
}

function getElemParams(selector) {
    const params = [];

    document.querySelectorAll(selector).forEach(function (tr) {
        const param = getElemParam(tr);

        params.push(param);
    });

    return params;
}