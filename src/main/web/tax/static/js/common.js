//Tomcat中修改默认端口为4444
let baseURL = 'http://111.67.207.131.:4869/';

function request(url, data, method = 'GET', successCallback, showLoading = false) {
	showLoading && uni.showLoading({
		mask: true
	});
	uni.request({
		header: {
			/*对于 POST 方法且 header['content-type'] 为 
			application/x-www-form-urlencoded 的数据，会将数据转换为 query string。*/
			'Content-Type': 'application/x-www-form-urlencoded',
			'accessToken': uni.getStorageSync('accessToken')
		},
		url: baseURL + url,
		data: data,
		method: method,
		success: (res) => {
			console.log(res);
			let datas = res.data;
			if (datas.code == 0) {
				console.log("code=0");
				if (typeof successCallback == 'function') {
					successCallback(datas);
				}
			} else if (datas.code == 1) {
				console.log("code=1");
				uni.showToast({
					title: datas.message
				});
			} else if (datas.code == 4 || datas.code == 5) {
				console.log("code=4/5");
				try {
					let hasPassword = uni.getStorageSync('hasPassword');
					let hasPhone = uni.getStorageSync('hasPhone');
					let areaCode = uni.getStorageSync('areaCode');
					if (hasPassword && hasPhone != '' && hasPassword != '' && hasPhone != '') {
						let preloadFunction = function() {
							requset(url, data, method, successCallback, showLoading);
						}
						login(hasPhone, hasPassword, areaCode, preloadFunction)
					} else {
						uni.redirectTo({
							url: '/pages/me/login',
						});
					}
				} catch (e) {
					console.log(e);
				}
			} else if (datas.code == 44) {
				console.log("code=44");
				uni.showToast({
					title: "Account Number or password is incorrect",
					mask: true,
					duration: 2500,
					icon: "error"
				});
			}
		},
		fail: (e) => {
			console.log("请求失败");
			uni.showToast({
				title: 'error!'
			});
		},
		complete: () => {
			showLoading && uni.hideLoading();
		}
	});
}

function login(phone, password, areaCode, preloadFunction) {
	uni.request({
		url: baseURL + 'noAuth/login',
		method: 'POST',
		header: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		data: {
			phone: phone,
			password: password,
			areaCode: areaCode
		},
		success: (res) => {
			console.log(res);
			let datas = res.data;
			if (datas.code == 0) {
				uni.setStorageSync("accessToken", datas.data);
				if (typeof preloadFunction == 'function') {
					preloadFunction();
				}
			}
			//request(url, data, method, successCallback, showLoading);
			// } else if (datas.code == 44) {
			// 	uni.showToast({
			// 		title: "Account Number or password is incorrect",
			// 		mask: true,
			// 		duration: 2500,
			// 		icon: "error"
			// 	});
			// 	uni.redirectTo({
			// 		url: '/pages/me/login',
			// 	});
			// }
		},
		fail: (e) => {
			uni.showToast({
				title: 'error!',
				mask: true,
				duration: 2500,
				icon: "error"
			});
		},
		complete: () => {
			uni.hideLoading();
		}
	});
}
module.exports = {
	request: request
}
