<template>
	<view>
		<!-- 定义状态栏 -->
		<view class="status_bar">
		</view>
		<!-- 定义导航栏 -->
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="left" @click="goHome()">
				<uni-icons type="home-filled" size="30" color="#ffffff"></uni-icons>
			</view>
			<view slot="default" class="topBarText">{{$t('global.appName')}}</view>
			<view slot="right">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
		<view class="loginLogo">
			<view class="logo">
				<cover-image src="/static/images/loginLogo.png"></cover-image>
			</view>
		</view>
		<view class="loginArea">
			<view class="loginForm">
				<view class="phone">
				
					<uni-data-picker v-model="currentArea" :ellipsis="false" :clear-icon="false" :localdata="areaCode"
						popup-title="choose code"></uni-data-picker>
					<input type="number" v-model="phone" :placeholder="$t('login.form.phone')" />
				</view>
				<view class="password">
					<input password v-model="password" :placeholder="$t('login.form.password')" />
				</view>
				<button class="submitButton" @click="login()">{{$t('login.form.login')}}</button>
			</view>
			<view class="noAccount">{{$t('login.form.noAccount')}}</view>
			<view class="signUpText" @click="toRegister()">
				{{$t('login.form.signUpNow')}}
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				areaCode: [{
					text: '007 ( RU )',
					value: 7
				}, {
					text: '370 ( LT )',
					value: 370,
					selected: true
				}, {
					text: '001 ( US )',
					value: 1
				}],
				currentArea: 370,
				phone: '',
				password: '',
				accessToken: ''
			}
		},
		onLoad() {

		},
		methods: {
			goHome() {
				
				uni.switchTab({
					url: '/pages/index/index'
				});
			},
			login() {
				let phone = this.$data.phone;
				let password = this.$data.password;
				let areaCode = this.$data.currentArea;
				if (phone == "" || password == "" || areaCode == "") {
					uni.showToast({
						title: this.$t('login.canNotNull'),
						mask: true,
						duration: 2500
					});
				} else {
					console.log('aaaa');
					this.iTools.request('noAuth/login', {
						phone: phone,
						password: password,
						areaCode: areaCode
					}, 'POST', function(data) {
						console.log(222222);
						uni.setStorageSync('hasPhone', phone);
						uni.setStorageSync('hasPassword', password);
						uni.setStorageSync('accessToken', data.data)
						uni.setStorageSync('areaCode', areaCode);
						console.log(data.code);
						
						uni.switchTab({
							url: '/pages/index/index',

						});
					}, true);
				}
			},
			toRegister(){
				uni.navigateTo({
					url:'/pages/me/register'
				})
			}

		}

	}
</script>

<style>
	.status_bar {
		height: var(--status-bar-height);
		width: 100%;
	}

	.topBarText {
		display: flex;
		justify-content: center;
		align-self: center;
		width: 100%;
		font-size: 1.2rem;
		font-weight: bold;
	}

	.loginLogo>.logo {
		width: 40%;
		box-sizing: border-box;
		padding: 30px 0px;
		margin: 0px auto;
	}

	.loginLogo>.logo>cover-image {
		width: 100%;
	}

	.loginArea {
		padding: 10px 10px 0px 10px;
		box-sizing: border-box;
	}

	.loginForm {
		padding: 25px;
		box-sizing: border-box;
		background-color: white;
		border-radius: 7px;
	}

	.loginForm>.phone,
	.loginForm>.password {
		display: flex;
		align-items: center;
		margin-bottom: 20px;
	}

	.loginForm>.phone>input {
		border: 1px solid #e5e5e5;
		border-radius: 5px;
		font-size: 16px;
		padding-left: 15px;
		font-weight: bold;
		height: 40px;
		line-height: 38px;
		flex-grow: 1;
		border-top-left-radius: 0px;
		border-bottom-left-radius: 0px;
	}

	.loginForm>.password {
		height: 40px;
		line-height: 40px;
		flex-grow: 1;
		border: 1px solid #e5e5e5;
		box-sizing: border-box;
		padding-left: 15px;
		font-weight: bold;
	}

	.loginForm .input-value-border {
		border-right: none !important;
		border-top-right-radius: 0px !important;
		border-bottom-right-radius: 0px !important;
	}

	.submitButton {
		width: 80%;
		background-color: #007FFF;
		color: white;
		font-weight: bold;
		margin-top: 20px;
	}

	.noAccount {
		font-size: 20px;
		color: #333333;
		text-align: center;
		margin: 0px auto;
		margin-top: 20px;
	}

	.signUpText {
		text-align: center;
		color: #007FFF;
		font-weight: bold;
	}

	uni-page-body {
		height: 100%;
	}
</style>
