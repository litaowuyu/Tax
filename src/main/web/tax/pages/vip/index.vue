<template>
	<view>
		<iHeader></iHeader>
		<view class="person">
			<view class="usercenter">
				<view>
					<image src="../../static/images/avatar.png"></image>
				</view>
				<view class="personInfo">
					<view class="username">
						{{$t('index.me.username')}} :{{userInfo.phone}}
					</view>
					<view class="ID">
						{{$t('index.me.ID')}} :{{userInfo.id}}
					</view>
					<view class="inviteCode">
						{{$t('index.me.inviteCode')}} :{{userInfo.uni_id}}
					</view>
				</view>
				<view>
					<view class="vipText">
						VIP{{userInfo.vip_level}}
					</view>
				</view>
			</view>

		</view>
		<view class="btnArea">
			<button class="becomeBtn" @click="becomeVIP()">{{$t('vip.index.becomeVip')}}</button>
			<button class="cancelBtn" @click="cancelVIP()">{{$t('vip.index.cancel')}}</button>
		</view>
		<view class="profitArea">
			<view class="table">
				<v-table>
					<tr>
						<th class="vip"></th>
						<th>Profit today</th>
						<th>Profit this week</th>
						<th>Profit this mouth</th>
					</tr>
					
					<tr>
						<td class="vip">VIP1</td>
						<td>$ 1.5</td>
						<td>$ 10.5</td>
						<td>$ 45</td>
					</tr>
					<tr>
						<td class="vip">VIP2</td>
						<td>$ 15.75</td>
						<td>$ 110.25</td>
						<td>$ 472.5</td>
					</tr>
					<tr>
						<td class="vip">VIP3</td>
						<td>$ 108</td>
						<td>$ 756</td>
						<td>$ 3240</td>
					</tr>
					<tr>
						<td class="vip">VIP4</td>
						<td>$ 207</td>
						<td>$ 1449</td>
						<td>$ 6210</td>
					</tr>
				</v-table>
			</view>
		</view>
		<view class="vipArea">
			<view class="head">
				<v-tabs v-model="current" :tabs="tabs" field="name" @change="checkIndex()" lineHeight="1px"
					paddingItem='0px 20px'>
				</v-tabs>
			</view>
			<!-- v-if判断语句    v-for循环	 -->
			<view v-for="(item,index) in tabs" v-if="current==index" :key="index">
				<view class="content">{{item.content}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import iHeader from "component/iHeader.vue";
	export default {

		data() {
			return {
				current: 0,
				tabs: [{
					'name': 'VIP0',
					'content': " VIP income 0 is 2.5% of the product price and the margin is $0. One item can be purchased per day and the item's sales cycle is 0 hours. Example: The price of a box of Ferrero Rocher chocolates is $3, and the income from its sale (including taxes). The specified period is 3 * 0.025 * 1 day ≈ $0.08. Product tax refund fees will be refunded to your account in the final account balance."

				}, {
					'name': 'VIP1',
					'content': " The VIP1 profit is 3% of the product price and the margin is $2. You can buy one item per day, and that item has a 12-hour sales cycle. Example: The price of a box of GODIVA chocolates is $29, and the sales revenue (tax included) for the period you specify is 29 * 0.03 * 1 day ≈ $0.87. Merchandise tax refund fees will be refunded to your account in the account balance at the end of the sale."
				}, {
					'name': 'VIP2',
					'content': " The VIP2 profit is 3.5% of the product price and the margin is $10. You can buy one item per day, and the item has a 48-hour sales cycle. Example: Chateau Mouton sells for $450 and your sales revenue (including tax) for the period you specify is 450 * 0.035 * 2 days ≈ $31.5. Merchandise tax refund fees will be refunded to your account in the account balance at the end of the sale.  "
				}, {
					'name': 'VIP3',
					'content': " The VIP3 profit is 4% of the product price and the margin is $50. You can buy one item per day, and that item has a 72-hour sales cycle. Example: iPhone13 pro Max sells for $1300, and your sales revenue (including tax) for the specified period is 1300 * 0.04 * 3 days ≈ $156. The product tax refund fee will be refunded to your account in the account balance at the end of the sale. "
				}, {
					'name': 'VIP4',
					'content': " The VIP4 profit is 4.5% of the product price and the margin is $100. You can buy one item per day, and that item has a sales cycle of 120 hours. Example: A Gucci bag sells for $4,600 and your sales revenue (including tax) for the period you specify is 4,600 * 0.045 * 4 days ≈ $828. Merchandise tax refund commission will be refunded to your account with account balance at the end of the sale "
				}, {
					'name': 'VIP5',
					'content': 'Not started'
				}, {
					'name': 'VIP6',
					'content': 'Not started'
				}, {
					'name': 'VIP7',
					'content': 'Not started'
				}],
				userInfo: {}
			}
		},
		onShow() {

			let accessToken = uni.getStorageSync('accessToken');
			if (accessToken == '') {
				uni.navigateTo({
					url: '/pages/me/login'
				});
			} else {
				this.getUserInfo();
			}
		},
		methods: {
			changeTab(index) {
				console.log('当前选中的项：' + index)
			},
			checkIndex(index) {},
			getUserInfo(showLoading = false) {
				let that = this;
				this.iTools.request('Auth/me/userInfo', {}, 'GET', function(data) {
					that.$data.userInfo = data.data;
				}, showLoading);
			},
			becomeVIP() {
				uni.navigateTo({
					url: '/pages/vip/VIPcenter'
				})
			},
			cancelVIP() {
				let that = this;
				uni.showModal({
					title: 'Tip',
					content: 'Do you sure you want to cancel VIP?',
					cancelText: 'No',
					confirmText: 'Yes',
					success: function(res) {
						if (res.confirm) {
							console.log('用户点击确定');
							that.iTools.request('Auth/cancelVIP', {}, 'POST', function(result) {
								if (result.code == 0) {
									uni.showToast({
										title: "cancel VIP success",
										mask: true,
										duration: 2500
									});
									uni.navigateTo({
										url: "/pages/vip/index"
									})
								} else {
									uni.showToast({
										title: "cancel VIP fail",
										mask: true,
										duration: 2500
									})
								}
							}, true);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				})

			}


		},
		onLoad() {},
		components: {
			iHeader,

		},


	}
</script>

<style>
	.profitArea {
		background-color: white;
		border-radius: 7px;
		margin: 10px;

	}

	.vip {
		width: 0%;
		font-size: 13px;
		padding: 5px;
	}

	th {
		padding: 5px;
		margin-left: 10px;
		font-weight: 400;
		font-size: 13px;
		background-color: #f5f5f5;
		width: 1%;
	}

	td {
		text-align: center;
		padding: 5px;
	}

	.table {
		padding-top: 10px;
		table-layout: fixed;
	}

	.vipArea {
		background-color: white;
		margin: 10px;
		border-radius: 7px;
		heigth: 100%
	}

	.head {
		padding-top: 5px;
		border-radius: 7px;
		border-bottom: 1px dashed #C0C0C0;
	}

	.content {
		padding: 10px;
		height: 200px;
		font-size: 15px;
	}

	.vipText {
		border-radius: 5px;
		border: 1px solid #007FFF;
		box-sizing: border-box;
		padding: 5px 10px;
		font-size: 16px;
		font-weight: bold;
		color: #ffd700;
	}
</style>
