<template>
	<view class="">
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="register-title" slot="default">Tax Refund</view>
		</uni-nav-bar>
		<view class="taxrefund-main-body">
			<view class="index-button">
				<button class="index-btn" v-for="(item, index) in buttonData" :class="{'choicedButton':selectButCSSKey==index}"
					@click="changeButCSS(index)">
					<view class="font-area">{{item}}</view>
				</button>
			</view>
			<view class="user-check">
				<view class="VIP-level">
					<view class="level-choise" v-for="(item, index) in VIPData"
						:class="{'level-choised':selectVIPCSSKey==index}" @click="changeVIPCSS(index)">
						<text>{{item.name}}</text>
					</view>
				</view>
			</view>
			<view class="goods-area">
				<view class="goods-count" v-if="orderBtnCurrentIndex==0" v-for="(item,index) in goodslist">
					<view class="goods-item">
						<view class="goods-introduce">
							<text>Item：{{item.name}} </text>
							<text class="vipText">VIP{{item.vip_level-2}}</text>
						</view>
						<view class="goods-picAndInfo">
							<view class="goods-pic">
								<image src="/static/images/taxRefund_goodspic/taxRefund_goodspic/chok.jpeg"
									mode="aspectFill"></image>
							</view>
							<view class="goods-info">
								<view class="goods-info-detail"><text>price:${{item.price}}</text></view>
								<view class="goods-info-detail"><text>Expected reward:${{item.reward}}</text></view>
								<view class="goods-info-detail"><text>Reward in:{{item.hours}} h</text></view>
								<button @click="buyGoods(index)">Click to buy</button>
							</view>
						</view>
					</view>
				</view>
			</view>
			<uni-load-more color="#007AFF" :status="moreStatus"  ></uni-load-more>
			<!-- 正在进行中的订单 -->
			<view class="goods-area">
				<view class="goods-count" v-if="orderBtnCurrentIndex==1" v-for="(item,index) in processingOrderList">
					<!-- -->
					<view class="goods-item">
						<view class="goods-introduce">
							<text>Item：{{item.name}} </text>
							<text class="vipText">VIP{{item.vip_level-2}}</text>
						</view>
						<view class="goods-picAndInfo">
							<view class="goods-pic">
								<image src="/static/images/taxRefund_goodspic/taxRefund_goodspic/chok.jpeg"
									mode="aspectFill"></image>
							</view>
							<view class="goods-info">
								<view class="goods-info-detail"><text>price:${{item.price}}</text></view>
								<view class="goods-info-detail"><text>Expected reward:${{item.reward}}</text></view>
								<view class="goods-info-detail"><text>Reward in:${{item.finish_time}}</text></view>

							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 完成订单 -->
			<view class="goods-area">
				<view class="goods-count" v-if="orderBtnCurrentIndex==2" v-for="(item,index) in completedOrder">
					<!-- -->
					<view class="goods-item">
						<view class="goods-introduce">
							<text>Item：{{item.name}} </text>
							<text class="vipText">VIP{{item.vip_level-2}}</text>
						</view>
						<view class="goods-picAndInfo">
							<view class="goods-pic">
								<image src="/static/images/taxRefund_goodspic/taxRefund_goodspic/chok.jpeg"
									mode="aspectFill"></image>
							</view>
							<view class="goods-info">
								<view class="goods-info-detail"><text>price:${{item.price}}</text></view>
								<view class="goods-info-detail"><text>Expected reward:${{item.reward}}</text></view>
								<view class="goods-info-detail"><text>Reward in:</text></view>
								<button @click="completeOrder(item.goods_id)">Finish</button>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import util from '../../static/js/util.js';
	export default {
		data() {
			return {
				buttonData: ["Available Orders", "Processing Orders", "Completed Orders"],
				VIPData: {},
				goodslist: {},
				// totalGoodsList:[],
				selectButCSSKey: -1,
				selectVIPCSSKey: 0,
				processingOrderList: {},
				orderBtnCurrentIndex: 0,
				completedOrder: {},
				moreStatus:'',
				pageNum:1
				
			}
		},
		onShow() {
			console.log("Tax Refund加载");
			this.getViplist();
		},
		methods: {
			changeVIPCSS(index) {
				this.selectVIPCSSKey = index;
				this.getGoodsList(index);
				console.log("changeVIPCSS-->VIP_Level:" + index);
				// this.getProcessingOrder(index, 1);
				// this.getCompleteOrder(index, 2);
			},
			getGoodsList(index,isAppend=false) {
				let that = this;
				let id = index + 1;
				// let pageNum= isAppend == true ? that.$data.pageNum++ : 1;
				//let totalGoodsList= this.$data.totalGoodsList;
				this.iTools.request('noAuth/goods/list', {
					vipID: id,
					// pageNum:pageNum
				}, 'GET', function(data) {
					that.$data.goodslist = data.data;
					for (var i = 0; i < that.$data.goodslist.length; i++) {
						that.$data.goodslist[i].price = util.regFenToYuan(that.$data.goodslist[i].price);
						that.$data.goodslist[i].reward = util.regFenToYuan(that.$data.goodslist[i].reward);
					}
					// Object.assign(that.$data.goodslist, that.$data.goodslist);
					// console.log("这是goodslist数据");
					// console.log(that.$data.goodslist);
				}, true)
			},
			changeButCSS(index) {
				this.selectButCSSKey = index;
				if (index == 0) {
					this.orderBtnCurrentIndex = index;
					this.getGoodsList(0);
				} else if (index == 1) {
					this.selectVIPCSSKey = 0;
					this.orderBtnCurrentIndex = index;
					//getProcessingOrder(0)给函数传一个默认的参数:0。为了在点击Processing Order时也可以从后端拉取VIP等级为All的数据。
					//index参数是按钮的下表，用于后端区分订单状态（tax_order---->state）
					this.getProcessingOrder(0, index);
				} else if (index == 2) {
					this.selectVIPCSSKey = 0;
					this.orderBtnCurrentIndex = index;
					this.getCompleteOrder(0, index);
				}
			},
			getProcessingOrder(vipIndex, btnIndex) {
				console.log("getProcessingOrder-->vipIndex: " + vipIndex);
				console.log("getProcessingOrder-->btnIndex: " + btnIndex);
				let that = this;
				this.iTools.request('Auth/processingOrderList', {
					vip_level: vipIndex + 1,
					btnIndex: btnIndex
				}, 'GET', function(data) {
					// console.log(data.data);
					that.$data.processingOrderList = data.data;
					console.log(that.$data.processingOrderList);
					if (data.data.length > 0) {
						//console.log(1112222222);
						that.$data.processingOrderList[0].price = util.regFenToYuan(that.$data.processingOrderList[0].price);
						that.$data.processingOrderList[0].reward = util.regFenToYuan(that.$data.processingOrderList[0].reward);
						let minute = util.timeConversion(that.$data.processingOrderList[0].finish_time).minute;
						let hour = util.timeConversion(that.$data.processingOrderList[0].finish_time).hours;
						that.$data.processingOrderList[0].finish_time = hour + "h " + minute + "min";
						//console.log("price" + that.$data.processingOrderList[0].price);
						//that.$data.processingOrderList = that.$data.processingOrderList;
					}
					
				}, true);
			},
			//btnIndex参数是按钮的下表，用于后端区分订单状态（tax_order---->state）
			getCompleteOrder(vipIndex, btnIndex) {
				console.log("getCompleteOrder>vipIndex: " + vipIndex);
				console.log("getCompleteOrder>btnIndex: " + btnIndex);
				let that = this;
				this.iTools.request('Auth/processingOrderList', {
					vip_level: vipIndex + 1,
					btnIndex: btnIndex
				}, 'GET', function(data) {
					console.log(data.data);
					that.$data.completedOrder = data.data;
					if (data.data.length > 0) {
						that.$data.completedOrder[0].price = util.regFenToYuan(that.$data.completedOrder[0].price);
						that.$data.completedOrder[0].reward = util.regFenToYuan(that.$data.completedOrder[0].reward);
						console.log("price" + that.$data.completedOrder[0].price);
					}
				}, true);
			},
			completeOrder(goods_id) {
				//let goods_id = this.$data.completedOrder[index].id;
				console.log("goods_id"+goods_id);
				let that = this;
				this.iTools.request('Auth/competeOrder', {
					goods_id: goods_id
				}, 'POST', function(data) {
					that.$data.completedOrder = data;
					console.log(that.$data.completedOrder);
					if(that.$data.completedOrder.code==0){
						uni.showToast({
							title:"success",
							mask:true,
							duration:2500
						});
						that.$data.orderBtnCurrentIndex = -1;
					}
					else{
						uni.showToast({
							title:"error",
							icon:"error",
							mask:true,
							duration:2500
						})
					}
				}, true);
			},
			getViplist() {
				let that = this;
				this.iTools.request('noAuth/vipList', {}, 'GET', function(data) {
					that.$data.VIPData = data.data;
					that.changeVIPCSS(0);
					console.log(data)
				}, true)
			},
			buyGoods(index) {

				let goods_id = this.$data.goodslist[index].id;
				console.log(goods_id);
				this.iTools.request('Auth/buyGoods', {
					goods_id: goods_id
				}, 'POST', function(data) {
					if (data.data == 0) {
						uni.showToast({
							title: "Buy Success",
							mask: true,
							duration: 2500
						});
					} else if (data.data == 1) {
						uni.showToast({
							title: " Insufficient Balance",
							mask: true,
							duration: 2500
						});
					} else if (data.data == 2) {
						uni.showToast({
							title: "VIP level does not match",
							mask: true,
							duration: 2500
						});
					} else if (data.data == 3) {
						uni.showToast({
							title: "Orders in progress",
							mask: true,
							duration: 2500
						});
					}

				}, true)
			},
			//到达底部
			// onReachBottom(index) {
			// 	if (this.$data.moreStatus != 'noMore') {
			// 		this.getGoodsList(index,true);
			// 	}
			// }
		}
	}
</script>

<style>
	.vipText {
		box-sizing: border-box;
		font-size: 14px;
		padding: 0px 20px;

	}

	.font-area {
		font-size: 14px;
		font-weight: 400;
		flex-wrap: wrap;
		word-wrap: break-word;
	}

</style>
