<template>
	<view class="container">
		<uni-nav-bar  backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default" >Tutorial</view>
		</uni-nav-bar>
		<view class="indexDescription">
		<view class="item">
			<view class="btn_switch">
		      <button class="btn_anniu" @click="change(0)" :class="{ newStyle:0===number}"> How to make money?</button>
		      <button class="btn_anniu" @click="change(1)" :class="{ newStyle:1===number}">Purchase/Settlement</button>
		      <button class="btn_anniu" @click="change(2)" :class="{ newStyle:2===number}"> Deposit</button>
		      <button class="btn_anniu" @click="change(3)" :class="{ newStyle:3===number}">Withdraw</button>
			</view> 
		</view>
		<view class="tutorial-text">
			<view v-show="0===number">
				<text>
				After logging in, you should first need to bind your bank account and USDT address,and become a Sky Taxrefund certified member after being approved by the electronic tax refund center of the tax refund platform.
				When You will purchase the specified tax refund items on the platform, we will complete the online tax refund operation within the sales time specified for the tax refund items, After the products are delivered to the dealer for sale, you will receive the tax refund by bank transfer or USDT transfer (depending on the type of product refunded, there may be a tax refund The difference, see tax system).
		</text>
			</view>
			<view v-show="1===number">
				<text>
				After sign-up, go to 'Tax Refund', select item and make order.
				You may start with your free bonus for VIP0 items or make deposit for higher value items.
				
				Once our staff receive your order they will help to proceed with the purchase, tax refund, and resell offline. You can check these orders in 'Processing Orders' column.
				
				Once the process is done, please go to 'Finished Orders' and click 'Settlement' to collect your profit.
		</text>
			</view>
			<view v-show="2===number">
				<text>
					<br>
				1. Go to 'Me', click 'Deposit';

				2. Choose your preferred payment channel, input amount and your name. In case of using others' bank account or Gcash account for making deposit, input his/her name instead;

				3. Click 'Deposit now' at the bottom and complete the payment in the next page.
				</text>
			</view>
			<view v-show="3===number">
				<text>
				1. Go to 'Me' and click 'Withdrawal Methods';

				2. Add bank card with bank account details, and click save;Go back to 'Me' and click 'Withdraw';

				3. Select your destination account for withdrawal, input withdrawal amount and your Tax Refund password, then click 'Withdraw now' to submit the request;</text></view>  
			</view>
		</view>
	</view>
</template>

<script>
	export default {					
	data() {
		return {
			number: 0,
		  }
		},
     methods: {
          change: function (index) {
            this.number = index; //重要处
          },
		  toHome(){
				uni.switchTab({
					url:"/pages/index/index"
				})
			}
		},
	}
</script>
<style >
	ul li{
		display: block;
		float: left;
		}
	.indexDescription{
		display: flex;
		justify-content: space-between;
		padding: 10px;
	}
	.btn_switch{
		height: 300px;
		
	}
	.btn_switch>button{
		margin-bottom: 15px;
		height: 100px;
		width: 150px;
		line-height: 100px;
		word-wrap: break-word;
		font-size: 13px;
	}
	.btn_anniu{
	    padding: 1px 0;
	    font-size: 12px;
	    color: #000;
	    outline: none;
	    background: #fff;
	  }
	.tutorial-text{
		width: 56%;
		font-size: 13px;
	  }
	.newStyle{
	     color: white;
		 background-color: #30abff!important;
	     /* font-size: 15px; */
	 }


</style>