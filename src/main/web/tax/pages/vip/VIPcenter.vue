<template>
	<view class="m-content">
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">VIP Center</view>
		</uni-nav-bar>
		<view>
			<view class="levels b-r bg-ff" v-for="(item,index) in VIPType">
				<view class="icon">
					<image class="icon1" :src="item.img"></image>
				</view>
				<view class="content">
					<view class="vip-title">

						<view class="vip_level">
							<text class="text">Margin:</text>
							<text class="text">{{item.price}}</text>
							<text class="text">$</text>
						</view>
						<text class="color-bd">{{item.level}}</text>
					</view>
					<view>
						<button class="btn-bg" @click="toVIPRules()"> VIP Rules</button>
						<button class="btn-bg" @click="registerVIP(item.price)">Register</button>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				VIPType: [{
						img: "/static/images/vipcenter-pic/VIP-1.png",
						price: "2",
						level: "VIP1"
					},
					{
						img: "/static/images/vipcenter-pic/VIP-2.png",
						price: "10",
						level: "VIP2"
					},
					{
						img: "/static/images/vipcenter-pic/VIP-3.png",
						price: "50",
						level: "VIP3"
					},
					{
						img: "/static/images/vipcenter-pic/VIP-4.png",
						price: "100",
						level: "VIP4"
					},
				],
			}
		},
		methods: {
			getUserInfo(showLoading = false) {
				let that = this;
				this.iTools.request('Auth/me/userInfo', {}, 'GET', function(data) {
					that.$data.userInfo = data.data;
					that.$data.userInfo.balance = util.regFenToYuan(that.$data.userInfo.balance);
				}, showLoading);
			},
			toHome() {
				uni.switchTab({
					url: "/pages/index/index"
				});
			},
			registerVIP(param) {
				var areaCode = JSON.parse(window.localStorage.getItem("areaCode")).data;
				var telnumber = JSON.parse(window.localStorage.getItem("hasPhone"));
				var phoneNumber = String(areaCode) + String(telnumber);
				let that = this;
				let price = param;
				uni.showModal({
					title: 'Tips',
					content: 'Do you sure you want to become a VIP?',
					cancelText: "NO",
					confirmText: "Yes",
					success: function(res) {
						if (res.confirm) {
							that.iTools.request('noAuth/registerVIP', {
									price: price,
									phoneNumber: phoneNumber
								},
								'POST',
								function(data) {
									console.log(data.data.statusCode);
									if (data.data.statusCode == 1) {
										uni.showToast({
											title: "success", //未提示
											mask: true,
											duration: 2000
										})

									} else {
										uni.showToast({
											title:"You have reached this VIP level", //未提示
											mask: true,
											icon:"error",
											duration: 2000
										})
									}
								}, false);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}

					}
				});

			},
			toVIPRules() {
				uni.navigateTo({
					url: '/pages/index/VIPRules'
				})
			}
		}
	}
</script>

<style>
	.m-content {
	
	}

	.levels {
		display: flex;
		align-items: center;
		flex-wrap: nowrap;
		padding: 0.5rem 0.375rem 0.375rem 0.5rem;
		/* margin-bottom: 0.375rem; */
		margin: 10px;
		border-radius: 7px;
	}

	.b-r {
		/* border-radius: 8px !important; */
		margin: 10px;
		border-radius: 7px;
	}

	.bg-ff {
		background-color: #fff;
		padding: 12px 9px 9px 12px;
		/* margin-bottom: 9px; */
		margin: 10px;
		border-radius: 7px;
	}

	.icon {
		width: 2rem;
		flex: 0 0 2rem;

		align-items: center;
		display: inline-block;
	}

	image {
		width: 100%;
		display: block;
		margin: auto;
		border: 0 none;
		vertical-align: bottom;
		width: 48px;
		height: 51px;
	}

	.content {
		flex: 1;
		display: inline-block;
		flex-direction: column;
		justify-content: space-between;
		margin-left: 50px;
	}

	.vip-title {

		display: flex;

	}

	.vip_level {
		width: 35%;
	}

	.text {
		font-size: .86rem;
		color: #333;
		line-height: 1;
		font-weight: 600;
		margin-left: 6px;
	}

	.color-bd {
		color: #585858 !important;
		margin-left: 120px;
	}

	.btn-bg {
		font-size: 13px;
		padding: 2.4px 12px 2.4px 12px;
		margin-right: 0.1rem;
		margin-left: 0.75rem;
		display: inline-flex;
		min-width: 3rem;
		margin-top: 0.375rem;
		justify-content: center;
		align-items: center;
		border: 0;
		min-height: 30px;
		background-color: #30abff !important;
		color: #fff !important;
		border-radius: 8px !important;
	}
</style>
