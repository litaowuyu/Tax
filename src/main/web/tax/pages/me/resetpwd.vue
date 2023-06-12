<template>
	<view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">Reset Password</view>
		</uni-nav-bar>
		<view class="reset-body">
			<view class="resetpwd-number">
				<text>mobile number</text>
				<input type="text" placeholder="please enter number" disabled="true" />
			</view>
			<view class="OTP">
				<view class="OTP-input">
					<input type="text" v-model="verifyCode" placeholder="please enter OTP" />
				</view>
				<button class="OTP-sent-button" @click="sendSMS()">sent OTP</button>
			</view>
			<view class="password">
				<input type="password" v-model="password" placeholder="please enter password" />
			</view>
			<view class="password">
				<input type="password" v-model="repeatPassword" placeholder="please confirm password" />
			</view>

			<button class="resetpwd-submitButton" @click="resetpassword()">{{$t('login.form.submit')}}</button>
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
				repeatPassword: '',
				verifyCode: '',
				token:''
			}
		},
		methods: {
			toHome() {
				uni.switchTab({
					url: "/pages/me/index"
				});
			},
			resetpassword() {
				let that = this;
				let phone = that.$data.phone;
				let password = that.$data.password;
				let repeatPassword = that.$data.repeatPassword;
				let verifyCode= this.$data.verifyCode;
				if (password == "" || repeatPassword == "") {
					uni.showToast({
						title: "password not be null",
						mask: true,
						duration: 2500
					});
					return;
				} else {
					if (password != repeatPassword) {
						uni.showToast({
							title: "repeatPassword not equal to password",
							mask: true,
							icon:"error",
							duration: 2500,

						});
						return;
					} else {
						if (verifyCode == "") {
							uni.showToast({
								title: "VerifyCode Cannot be empty",
								icon:"error",
								mask: true,
								duration: 2500,

							});
							return
						} else {
							let baseURL = 'http://111.67.207.131.:4869/';
							uni.showModal({
								title: 'Tips',
								content: 'You confirm that you want to change the password?',
								cancelText: "NO",
								confirmText: "Yes",
								success: function (res) {
									if (res.confirm) {
										uni.request({
											header: {
												'Content-Type': 'application/x-www-form-urlencoded',
												'accessToken':uni.getStorageSync('accessToken'),
												'token':that.$data.token
											},
											url: baseURL + "Auth/resetPassword",
											data: {
												verifyCode: verifyCode,
												password: password,
												
											},
											method:'GET',
											success(data) {
												console.log(1111111);
												
												let datas = data.data;
												console.log(datas)
												console.log(datas);
												if (datas.code == 0) {
														uni.showToast({
															title: "reset password success", //加上switchTab不提示showToast.title
															mask: true,
															duration: 2500
														});
														uni.switchTab({
															url: "/pages/me/index"
														});
													} else {
														uni.showToast({
															title: "reset password fail",
															icon:"error",
															mask: true,
															duration: 2500
														});
													}
											}
										})
									} else if (res.cancel) {
										console.log('用户点击取消');
									}
								}
							});

						}
					}
				}
			},
			sendSMS() {
				let that= this;
				this.iTools.request('noAuth/utils/getVerifyCode', {
					phone: 111111,
				}, 'GET', function(data) {
					console.log('验证码已发送11111111111');
					uni.showToast({
						title:"The verification code sent successfully",
						duration:2500,
						mask:true
					})
					that.$data.token=data.data;
					console.log(that.$data.token);
			
				}, true)
			}
		}
	}
</script>

<style>
	.reset-body {
		margin: 10px;
		border-radius: 7px;
		padding: 10px;
		background-color: white;
	}

	.resetpwd-number {
		display: flex;
		align-items: center;
		height: 45px;
		border: 1px solid #DCDCDC;
		border-radius: 10px;
		margin-bottom: 10px;
	}

	.resetpwd-number>text {
		margin-left: 10px;
	}

	.resetpwd-number>input {
		display: inline-block;
		margin-left: 30px;
	}

	.OTP {
		display: flex;
		justify-content: center;
	}

	.OTP-input {

		display: flex;
		align-items: center;
		width: 55%;
		height: 45px;
		border: 1px solid #DCDCDC;
		border-radius: 10px;
		margin-bottom: 10px;
	}

	.OTP-sent-button {
		display: inline-block;
		width: 40%;
		height: 45px;
		color: white;
		background-color: #007AFF;
	}

	.password {
		display: flex;
		align-items: center;
		height: 45px;
		border: 1px solid #DCDCDC;
		border-radius: 10px;
		margin-bottom: 10px;
	}

	.OTP-input>input,
	.password>input {
		width: 100%;
		margin-left: 10px;
	}

	.resetpwd-submitButton {
		width: 100%;
		color: white;
		background-color: #007AFF;
	}
</style>
