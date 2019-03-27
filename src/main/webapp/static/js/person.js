function initPerson(data) {
    $('.js-person-info-panel').on(
        "click",function () {
            $('.js-person-info').css('display','');
            $('.js-laundry-worker').css("display", 'none');
            $('.js-laundry-shop').css("display", 'none');
            $('.js-laundry-order').css("display", 'none');
            $('.js-laundry-goods').css("display",'none');
            $('.js-business-analyse').css("display",'none');
            $('.js-setting').css('display','none');
            $('.js-account-lab').text(data.adminNo);
            $('.js-name-lab').text(data.name); //姓名
            $('.js-sex-lab').text(data.sex);//性别
            $('.js-tel-num-lab').text(data.telNum); //手机号
            $('.js-email-lab').text(data.email);//邮箱
            $('.js-email-lab').text();//邮箱
            $('.js-role-lab').text(data.roleName);
            $('.js-shop-lab').text(data.shopName);
        }
    );
    /**
     * 点击修改个人信息
     */
    $('.js-person-update-btn').on(
        "click",function () {
            $('.js-title-worker').text('修改个人信息');
            $('.js-worker-no-modal-div').css("display", '');
            $('.js-admin-no-modal').text(data.adminNo);
            $('.js-name-modal-worker').val(data.name); //姓名
            $('.js-tel-num-modal-worker').css("display", 'none'); //手机号
            $('.js-modal-tel-num-lab').text('所属分类');
            $('.js-worker-sex-sel').val(data.sex); //性别
            $('.js-email-per-div').css("display", 'none'); //邮箱

            $('.js-tel-num-worker-lab').css("display", '');
            $('.js-tel-num-worker-lab').text(data.roleName);
            //所属门店
            $('.js-role-lab').text('所属门店');
            $('.js-worker-cate-sel-lab').css("display", '');
            $('.js-worker-cate-sel-lab').text(data.shopName);
            $('.js-worker-cate-sel-div').css("display", 'none');
            action = "personEdit";
        }
    );
}