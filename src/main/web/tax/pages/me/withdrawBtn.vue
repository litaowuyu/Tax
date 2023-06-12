<template>
	<view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">Withdraw</view>
		</uni-nav-bar>
		<view class="withdraw_mainBody">
			<view class="withdraw_user_input_area">
				<view class="withdraw_mobile_num">
					<view><text>Mobile number</text></view>
					<view class="user_inputNum"><input type="text" placeholder="" disabled="true" /></view>
				</view>
				<uni-data-picker class="select" v-model="default_account" :ellipsis="false" :clear-icon="false"
					:localdata="accountList">
				</uni-data-picker>
				<view class="withdraw_userCount_area">
					<view class="userCount_tips_words"><text>用户的账户通过加密转换得到</text></view>
				</view>
				<view class="withdraw_dollar">
					<view class="withdraw_dollar_input"><input type="text" v-model="balance" placeholder="$20.00~" />
					</view>
				</view>
				<view class="withdraw_OTP">
					<view class="withdraw_OTP_input"><input type="text" v-model="verifyCode"
							placeholder="please enter OTP" /></view>
					<button class="send_OTP_button" @click="sendSMS()">send OTP</button>
				</view>
			</view>
			<view class="withdraw_tips_area">
				<view class="tips_each_line"><text>Tips:</text></view>
				<view class="tips_each_line"><text>1. Make sure your wallet address is correct.</text></view>
				<view class="tips_each_line"><text>2. Start withdrawal request.</text></view>
				<view class="tips_each_line"><text>3. Select your wallet to receive the withdrawal.</text></view>
				<view class="tips_each_line"><text>4. The approval will be done and you will receive the
						cryptocurrency.</text></view>
				<view class="tips_each_line"><text>5. If you don't receive it, please contact our customer support
						team.</text></view>
				<button class="withdraw_confirm_button" @click="withDrawBalance()">Confirm</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				accountList: [{
					text: 'TRC20',
					value: 'TRC20',
					selected: true
				}, {
					text: '1',
					value: '1'

				}, {
					text: '2',
					value: '2'
				}],
				default_account: 'TRC20',
				balance: '',
				verifyCode: '',
				token: ''
			}
		},
		methods: {
			toHome() {
				uni.switchTab({
					url: "/pages/me/index"
				});
			},
			withDrawBalance() {
				let that = this;
				let inputBalance = that.$data.balance;
				let verifyCode = that.$data.verifyCode;
				console.log("打印的验证码" + verifyCode)
				if (inputBalance == "") {
					uni.showToast({
						title: " withdraw money cannot be empty",
						mask: true,
						duration: 2500
					})
				} else {
					if (inputBalance < 20) {
						uni.showToast({
							title: "withdraw money cannot be less than $20",
							mask: true,
							duration: 2500
						})
						//输入不为空，并且提现金额大于20
					} else {
						if (verifyCode == "") {
							uni.showToast({
								title: "VerifyCode Cannot be empty",
								mask: true,
								duration: 2500
							})

						} else {
							let baseURL = 'http://111.67.207.131.:4869/';
							uni.showModal({
								title: 'Tips',
								content: 'Do you confirm that you want to cash?',
								cancelText: "NO",
								confirmText: "Yes",
								success: function(res) {
									if (res.confirm) {
										uni.request({
											header: {
												'Content-Type': 'application/x-www-form-urlencoded',
												'accessToken': uni.getStorageSync('accessToken'),
												'withdraw_token': that.$data.token
											},
											url: baseURL + "Auth/changeUserBalance",
											data: {
												balance: inputBalance * 100,
												verifyCode: verifyCode
											},
											method: 'GET',
											success(result) {
												if (result.data.data == 0) {
													uni.showToast({
														title: result.message, //未提示
														mask: true,
														duration: 2000
													});
													uni.switchTab({
														url: "/pages/me/index"
													});
												} else if (result.data.data == 7) {
													uni.showToast({
														title: "Withdraw Fail",
														mask: true,
														icon: "error",
														duration: 2500
													})
												} else if (result.data.data == 8) {
													uni.showToast({
														title: "verify Code Fail",
														mask: true,
														icon: "error",
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
				let that = this;
				this.iTools.request('noAuth/utils/getVerifyCode', {
					phone: 22222,
				}, 'GET', function(data) {
					console.log('验证码已发送');
					uni.showToast({
						title: "The verification code sent successfully",
						duration: 2500,
						mask: true
					})
					that.$data.token = data.data;
					// console.log(that.$data.token);

				}, true)
			}
		},

	}
</script>

<style>
	.withdraw_mainBody {
		padding: 10px;
		background-color: white;
		margin: 10px;
		border-radius: 7px;
	}

	.withdraw_user_input_area {
		border-radius: 7px;

	}

	.withdraw_mobile_num,
	.withdraw_TRC20_USDT,
	.withdraw_dollar {
		border: 1px solid #F1F1F1;
		border-radius: 7px;
		height: 35px;
		display: flex;
		align-items: center;
		font-size: 13px;
		margin-bottom: 10px;
		padding: 5px 10px;
		background-color: white;
	}

	.withdraw_mobile_num>.user_inputNum {
		margin-left: 20px;
	}

	.withdraw_TRC20_USDT>.TRC20_USDT_tips_words {
		width: 80%;
	}

	.withdraw_TRC20_USDT>.TRC20_USDT_icon {
		margin-left: 50px;
	}

	.withdraw_userCount_area {
		border: 1px solid #F1F1F1;
		border-radius: 10px;
		height: 65px;
		font-size: 13px;
		margin: 10px 0px;
		padding: 8px;
		background-color: white;
	}

	.withdraw_OTP {
		display: flex;
		align-items: center;
		margin-bottom: 10px;
	}

	.withdraw_OTP_input {
		border: 1px solid #F1F1F1;
		border-radius: 10px;
		height: 35px;
		width: 40%;
		font-size: 13px;
		display: flex;
		align-items: center;
		padding: 5px 10px;
		background-color: white;
	}

	.send_OTP_button {
		width: 50%;
		background-color: #30ABFF;
		color: white;
		margin-left: 15px;
	}

	.withdraw_tips_area {
		height: 280px;
		margin-top: 5px;
		padding: 10px;
	}

	.tips_each_line {
		font-size: 12px;
		margin-top: 10px;
	}

	.withdraw_confirm_button {
		margin-top: 10px;
		background-color: #30ABFF;
		color: white;
	}

	.select {
		background-color: white;
	}
</style>
