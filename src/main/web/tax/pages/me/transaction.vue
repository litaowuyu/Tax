<template>
	<view class="transaction-body">
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="back()">
				<uni-icons type="back" size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default">{{$t('me.transaction.name')}}</view>
		</uni-nav-bar>
		<view class="transaction-mainbody">
			<view class="orderbtn">
				<view class="orderbtn-area">
					<view class="orderButtons">
						
						<button @click="loadRechargeProfit()">recharge</button>
						<button @click="loadWithdrawProfit()">withdraw</button>
						<button @click="loadOrderProfit()">TaxRefund</button>
						<button @click="loadTeamProfit()">team</button>
						
					</view>
				</view>
			</view>
			<view class="transaction-item">
				<view class="transaction-area">
					<view class="transaction-detail" v-for="(item, index) in transactionData">
						<view class="transaction-item-detail"><text>交易时间 : {{item.time}}</text></view>
						<view class="transaction-item-detail"><text>交易项目 : {{item.name}}</text></view>
						<view class="transaction-item-detail"><text>交易盈利 : {{item.reward}}</text></view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import util from '../../static/js/util.js';
	export default {
		data(){
			return{
				orderButton:["invite", "order", "recharge", "withdraw"],
				transactionData:{},
				subSymbol:"- $",
				addSymbol:"+ $"
			}
		},
		methods:{
			back(){
				uni.switchTab({
					url:"/pages/me/index"
				})
			},
			loadTeamProfit(){
				let that = this;
				this.iTools.request("Auth/getUserTeam",{},"GET",function(result){
					for(let index in result.data){
						result.data[index].reward =that.$data.addSymbol + util.regFenToYuan(result.data[index].reward)
					}
					that.$data.transactionData = result.data;
				},true)
			},
			loadOrderProfit(){
				let that = this;
				this.iTools.request("Auth/getUserOrder",{},"GET",function(result){
					for(let index in result.data){
						result.data[index].reward =that.$data.addSymbol + util.regFenToYuan(result.data[index].reward)
					}
					that.$data.transactionData = result.data;
				},true)
			},
			loadWithdrawProfit(){
				let that = this;
				this.iTools.request("Auth/getUserWithdraw",{},"GET",function(result){
					for(let index in result.data){
						result.data[index].reward =that.$data.subSymbol + util.regFenToYuan(result.data[index].reward);
					}
					that.$data.transactionData = result.data;
				},true)
			},
			loadRechargeProfit(){
				let that = this;
				this.iTools.request("Auth/getRechargeRecord",{},"GET",function(result){
					for(let index in result.data){
						result.data[index].reward =that.$data.addSymbol + util.regFenToYuan(result.data[index].reward);
					}
					that.$data.transactionData = result.data;
				},true)
			}
		}
	}
</script>

<style>
	.uni-page-wrapper{
		/* background-color: white; */
	} 
	.transaction-mainbody{
		padding: 7px;
		display: flex;
		justify-content: space-between;
		height: 100%;
		background-color: white;
	}
	.orderbtn{
		width: 30%;
		height: 100%;
		
	}
	.orderbtn-area{
		height: 100%;
		overflow-y: scroll;
	}
	.orderButtons{
		width: 90%;/*在.orderbtn-area:width:30%的基础上设置为90%*/
		margin-left: 5%;
		margin-top: 5px;
	}
	.orderButtons>button{
		height: 80px;
		line-height: 80px;
		font-size: 13px;
		margin-bottom: 15px;
		word-wrap: break-word;
	}
	.transaction-item{
		width: 65%;
		height: 840px;
		/* border: 1px solid black; */
		padding: 5px;
	}
	.transaction-area{
		height: 100%;
		overflow-y: scroll;
	}
	.transaction-detail{
		border: 1px solid #dedede;
		border-radius: 10px;
		padding: 5px;
		margin-bottom: 15px;
	}
	.transaction-item-detail{
		margin-bottom: 5px;
		margin-left: 10%;
		font-size: 13px;
		overflow: scroll;
	}
</style>
