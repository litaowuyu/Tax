<template>
	<view>
		<view class="status_bar">
		</view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="left" @click="goHome()">
				<uni-icons type="arrow-left" size="30" color="white"></uni-icons>
			</view>
			<view slot="default" class="topBarText">{{$t('index.me.recharge')}}</view>
			<view slot="right">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
		<view class="paymentArea">
			<view class="items">
				<view class="paymentOne">
					<uni-data-picker v-model="currentpayment" :clear-icon="false" :localdata="payment"
						popup-title="choose payment">
					</uni-data-picker>
				</view>
				<view class="paymentTwo">
					<uni-data-picker v-model="currentpaymentTwo" :clear-icon="false" :localdata="paymentTwo"
						popup-title="choose payment"></uni-data-picker>
				</view>
				<input type="number" v-model="money" placeholder="  $ 20 ~" />
				<view class="content">
					<text class="title">
						{{currentContent.title}}
					</text>
					<text class="content">
						{{currentContent.content}}
					</text>
				</view>
				<button type="primary" @click="submit()">{{$t("me.recharge.confirm")}}</button>

			</view>
		</view>

	</view>
</template>

<script>
	export default {
		onShow() {
			let that = this;
			this.iTools.request('noAuth/getPayment', {}, 'GET', function(data) {
				let datas = data.data;

				for (let x in datas) {
					datas[x].text = datas[x].payment_name;

					datas[x].value = datas[x].id;

				}
				datas[0].selected = true;
				that.$data.payment = datas;

			});
			let currentLocal = uni.getLocale().toUpperCase();
			this.$data.currentContent = this.$data['currentContent_' + currentLocal];
		},
		data() {
			return {
				money: '',
				payment: [],
				paymentTwo: [{
					value: 'TRC20_USDT',
					text: ' TRC20_USDT',
					selected: true
				}],
				currentpaymentTwo: "TRC20_USDT",
				currentpayment: 1,
				currentContent: {
					title: '',
					content: ''
				},
				currentContent_EN: {
					title: 'Tips:',
					content: "\n\n1. Make sure you deposit to the correct address.\n" +
						"\n2. Do not deposit any other assets apart from the type of crypto currency supported. Example: a TRC20 USDT wallet only accepts TRC20 USDT, do not transfer ERC20 USDT to avoid losses of any kind.\n" +
						" \n3. The transfer will be recognized normally within 5 minutes. In case of channel traffic, please contact our customer service team for assistance.\n\n"
				}

			}
		},
		methods: {
			goHome() {
				uni.switchTab({
					url: '/pages/me/index'
				});
			},
			submit() {
				let that=this;
				let money = this.$data.money;

				if (money < 20) {
					uni.showToast({
						title: this.$t("me.recharge.tips"),
						icon: "error",
						mask: true,
						duration: 2000
					});
					return false;
				}
				let currentpayment = this.$data.currentpayment;
				let currentpaymentTwo = this.$data.currentpaymentTwo;

				uni.showModal({
					title: 'Tips',
					content: 'You sure you want to recharge it?',
					cancelText: "NO",
					confirmText: "Yes",
					success: function(res) {
						if (res.confirm) {
							that.iTools.request('Auth/recharge', {
								payment_id: currentpayment,
								payment_type: currentpaymentTwo,
								money: money * 100
							}, 'POST', function(data) {
								uni.showToast({
									title: "success",
									mask: true,
									duration: 2000
								});
								
								uni.switchTab({
									url: '/pages/me/index'
								})
							}, true)
							
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
				
			}

		}
	}
</script>

<style>

</style>
