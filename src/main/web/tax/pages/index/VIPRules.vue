<template >
	<view style="background-color: white;">
		<uni-nav-bar  backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default" >VIP Rules</view>
		</uni-nav-bar>
		<view class="becomVip_mainBody">
			<view class="becomVip_title">
				<view class="title_word"><text>VIP Rules</text></view>
			</view>
			<view class="vipLevel_introduce">
				<view class="vipLevel_detail" v-for="(item,index) in vipContent" >
					<view class="vipLevel_title">
						<view class="vipLevel_title_word"><text>{{item.title}}</text></view>
						<view class="vipLevel_title_icon">
							<uni-icons type="bottom" size="20" color="#bdbdbd"></uni-icons>
						</view>
					</view>
					<view class="vipLevel_content">
						<view class="vipLevel_content_pic">
							
								<view class="vipText">
									{{item.title}}
								</view>
							
							<!-- <image src="../../static/images/avatar.png" mode="widthFix"></image> -->
						</view>
						<view class="vipLevel_content_detail">
							<text>{{item.vipIntroduce}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return {
				vipLevel:["VIP0", "VIP1", "VIP2", "VIP3", "VIP4", "VIP5", "VIP6", "VIP7"],
				vipContent:[
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP0",
						vipIntroduce:"VIP income 0 is 2.5% of the product price and the margin is $0. One item can be purchased per day and the item's sales cycle is 0 hours. Example: The price of a box of Ferrero Rocher chocolates is $3, and the income from its sale (including taxes). The specified period is 3 * 0.025 * 1 day ≈ $0.08. Product tax refund fees will be refunded to your account in the final account balance."
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP1",
						vipIntroduce:" The VIP1 profit is 3% of the product price and the margin is $2. You can buy one item per day, and that item has a 12-hour sales cycle. Example: The price of a box of GODIVA chocolates is $29, and the sales revenue (tax included) for the period you specify is 29 * 0.03 * 1 day ≈ $0.87. Merchandise tax refund fees will be refunded to your account in the account balance at the end of the sale. "
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP2",
						vipIntroduce:" The VIP2 profit is 3.5% of the product price and the margin is $10. You can buy one item per day, and the item has a 48-hour sales cycle. Example: Chateau Mouton sells for $450 and your sales revenue (including tax) for the period you specify is 450 * 0.035 * 2 days ≈ $31.5. Merchandise tax refund fees will be refunded to your account in the account balance at the end of the sale.  "
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP3",
						vipIntroduce:" The VIP3 profit is 4% of the product price and the margin is $50. You can buy one item per day, and that item has a 72-hour sales cycle. Example: iPhone13 pro Max sells for $1300, and your sales revenue (including tax) for the specified period is 1300 * 0.04 * 3 days ≈ $156. The product tax refund fee will be refunded to your account in the account balance at the end of the sale. "
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP4",
						vipIntroduce:" The VIP4 profit is 4.5% of the product price and the margin is $100. You can buy one item per day, and that item has a sales cycle of 120 hours. Example: A Gucci bag sells for $4,600 and your sales revenue (including tax) for the period you specify is 4,600 * 0.045 * 4 days ≈ $828. Merchandise tax refund commission will be refunded to your account with account balance at the end of the sale "
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP5",
						vipIntroduce:"Not started"
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP6",
						vipIntroduce:"Not started"
					},
					{
						img:"../../static/images/defaultVipLevel.png",
						title:"VIP7",
						vipIntroduce:"Not started"
					},
				]
			}
		},
		methods:{
			toHome(){
				uni.navigateTo({
					url:"/pages/index/VIPcenter"
				})
			}
		}
	}
</script>

<style>
	.becomVip_mainBody{
		padding: 10px;
		height: 800px;
	}
	.becomVip_title{
		height: 80px;
		display: flex;
		align-items: center;
	}
	.title_word{
		margin: 0 auto;
		height: 40px;
		line-height: 40px;
		width: 50%;
		text-align: center;
		font-size: 14px;
		border-top: 2px solid #1677fe;
		border-bottom: 2px solid #1677fe;
	}
	.vipLevel_detail{
		background-color: #f5f5f5;
		border-radius: 10px;
		margin-bottom: 10px;
	}
	.vipLevel_detail1{
		background-color: #f5f5f5;
		/* height: 150px; */
		border-radius: 10px;
		margin-bottom: 10px;
	}
	.vipLevel_title{
		display: flex;
		align-items: center;
		line-height: 40px;
	}
	.vipLevel_title_word{
		margin-left: 5%;
		width: 85%;
		font-size: 14px;
	}
	.vipLevel_content{
		background-color: white;
	}
	.vipLevel_content_pic{
		width: 50px;
		margin: 0 auto;
		margin-top: 5px;
	}
	.vipLevel_content_pic>image{
		width: 50px;
	}
	.vipLevel_content_detail{
		padding: 10px;
		font-size: 13px;
	}
	.vipText{
		color:#e7e700 ;
		font-weight: bold;
		font-size: 22px;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	.viptexts{
		position: absolute;
		top: 85px;
		right: 38%;
		font-family: Sans-serif, Times, serif;
		font-size: 25px;
		border-top-style: solid;
		border-bottom-style:solid ;
		border-color: #007AFF;
		border-width: 2px;
		padding-left: 100px;
		padding-right: 100px;
	}
	.picture1{
		position: absolute;
		top: 200px;
		width: 100%;
		height: 200px;
	}
	.picture2{
		position: absolute;
		top:410px
	}
	.picture3{
		position: absolute;
		top:610px
	}
	.picture4{
		position: absolute;
		top:810px
	}
	.picture5{
		position: absolute;
		top:1010px
	}
	.picture6{
		position: absolute;
		top:1210px
	}
	.picture7{
		position: absolute;
		top:1410px
	}
</style>
