var localLng = ''
var localLat = ''
$('.js-select-address').on(
    "click",function () {
        getLocalAddress();
    }
);
function getLocalAddress() {
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        // var point = new BMap.Point(116.331398,39.897445);
        // map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
        // if(this.getStatus() == BMAP_STATUS_SUCCESS){
        //     map.panTo(r.point);
        //     localLng = r.point.lng;
        //     localLat = r.point.lat;
            // var points = new BMap.Point(localLng,localLat);
            // map.centerAndZoom(points, 12);
            // var marker = new BMap.Marker(points);// 创建标注
            // map.addOverlay(marker);             // 将标注添加到地图中
    //         marker.addEventListener("click",function(e){
    //             var p = marker.getPosition();       //获取marker的位置
    //             var gc = new BMap.Geocoder();//地址解析类
    //             var Mpoints = new BMap.Point(localLng,localLat);
		// 	    gc.getLocation(e.point, function (rs) {
    //              var addComp = rs.addressComponents;
    // //              console.log(rs.address);//地址信息
    // // //              document.getElementById("txtposition").value = rs.address;
    // // //                 alert(rs.address);//弹出所在地址
    // //                 alert(rs.address);
    //
    //          });
    //         });

            // map.addEventListener('moving',function(){
            //     var center=map.getCenter();
            //     marker.setPosition(center);//更新marker
            // });
            // marker.enableDragging();      // 可拖拽

    //     }
    //     else {
    //         alert('failed'+this.getStatus());
    //     }
    // },{enableHighAccuracy: true});
}
