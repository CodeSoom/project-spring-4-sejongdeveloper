//쿼리스트링 문자열을 얻는다.
function getQueryString(selector) {
    if (selector === undefined) {
        selector = '#searchForm';
    }

    let queryString = '';

    document.querySelector(selector).querySelectorAll('input').forEach(function (obj, index) {
        if (index === 0) {
            queryString = `?${obj.name}=${obj.value}`;
            return;
        }

        queryString += `&${obj.name}=${obj.value}`;
    });

    return queryString;
}

//페이지 번호를 변경하고 페이지 목록을 호출한다.
function changePage(page) {
    document.querySelector('#page').value = page;
    loadList();
}

//주어진 조건으로 페이징 처리를 한다.
function setPagination(data) {
    const pageNumber = data.pageable.pageNumber;
    let previous = '';
    let next = '';

    if (data.first) {
        previous = 'disabled';
    }

    if (data.last) {
        next = 'disabled';
    }

    document.querySelector('.pagination').innerHTML = `
                <li class="page-item ${previous}">
                    <a class="page-link" href="javascript:changePage(${pageNumber - 1})">Previous</a>
                </li>
                <li class="page-item ${next}">
                    <a class="page-link" href="javascript:changePage(${pageNumber + 1})">Next</a>
                </li>
            `;
}