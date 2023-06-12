<template>
	<view class="">
		<uni-nav-bar  backgroundColor="#007fff" color="#ffffff">
			<view class="register-title" slot="default" >My Team</view>
		</uni-nav-bar>
		<view class="myTeam_mainBody">
			<view class="myTeam_userInfo">
				<view class="usercenter">
					<view>
						<image src="../../static/images/avatar.png"></image>
					</view>
					<view class="personInfo">
						<view class="username">
							<!-- 读取对象中获取数据  {{对象名.键}} 例如{{userinfo.username}}-->
							{{$t('index.me.username')}} :
							{{userInfo.phone}}
						</view>
						<view class="ID">
							{{$t('index.me.ID')}} :
							{{userInfo.id}}
							
						</view>
						<view class="inviteCode">
							{{$t('index.me.inviteCode')}} :
							{{userInfo.uni_id}}
							
						</view>
					</view>
					<view class="vip">
						<view class="vipText">
							VIP{{userInfo.vip_level}}
						</view>
					</view>
				</view>
				</view>
				
				<view class="myTeam_Btn">
					<button @click="toTeamrules()">Team rules</button>
					<button @click="toLink()">Share</button>
				</view>
				
				<view class="myTeam_withdraw">
					<view class="myTeam_withdraw_item1">
						<view class="myTeam_withdraw_item_detail"><text>Total deposit team: $ 1.00</text></view>
						<view class="myTeam_withdraw_item_detail"><text>Team deposit: $ 20.00</text></view>
						<view class="myTeam_withdraw_item_detail"><text>Commission today: $ 0.00</text></view>
					</view>
					<view class="myTeam_withdraw_item2">
						<view class="myTeam_withdraw_item_detail"><text>Total sign-up team: $ 82.00</text></view>
						<view class="myTeam_withdraw_item_detail"><text>Team withdrawal: $ 0.00</text></view>
						<view class="myTeam_withdraw_item_detail"><text>Total commission: $ 0.00</text></view>
					</view>
				</view>
				
				<view class="myTeam_members">
					<view class="myTeam_members_header">
						<view class="myTeam_members_level" v-for="(item,index) in memberLevelData"
						:class="{'myTeam_members_level_choised':selectMemberLevelCSSKey==index}" 
						@click="changeMemberLevelCSS(index)">
							<text>{{item}}</text>
						</view>
					</view>
					<view class="myTeam_members_info">
						<view class="myTeam_members_titles">
							<view class="members_title1"><text>Mobile</text></view>
							<view class="members_title2"><text>Balance</text></view>
							<view class="members_title3"><text>Deposit</text></view>
							<view class="members_title4"><text>Sign-up Date</text></view>
						</view>
						<view class="members_info" v-for="(item, index) in memberUserData">
							<view class="members_title1"><text>{{item.phone}}</text></view>
							<view class="members_title2"><text>{{item.balance}}</text></view>
							<view class="members_title3"><text>$0.00</text></view>
							<view class="members_title4"><text>{{item.create_time}}</text></view>
							</view>
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
		  memberLevelData:["lv1 member","lv2 member"],
		  memberUserData:{},
		  userInfo:{},
		  selectMemberLevelCSSKey:0
	  }
    },
	onShow() {
		this.loadUserLv1MemberInfo();
		this.loadUserInfo();
	},
    methods: {
		toTeamrules(){
			uni.navigateTo({
				url:"/pages/myTeam/teamRules"
			})
		},
		//改变点击的CSS样式
		changeMemberLevelCSS(index){
			this.selectMemberLevelCSSKey=index;
			if(index == 0){
				this.loadUserLv1MemberInfo();
			}
			if(index == 1){
				this.loadUserLv2MemberInfo();
			}
		},
		//调取Lv1Member用户信息
		loadUserLv1MemberInfo(){
			let that = this;
			this.iTools.request("Auth/getLv1Member",{},"GET",function(result){
				that.$data.memberUserData = result.data;
			},true)
		},
		//调取Lv2Member用户信息
		loadUserLv2MemberInfo(){
			let that = this;
			let userArr = that.$data.memberUserData;
			let phoneArr = [];
			for(let index in userArr){
				phoneArr.push(userArr[index].phone);
			}
			//console.log(phoneArr);
			this.iTools.request("Auth/getLv2Member", {phoneArr:phoneArr}, "GET", function(result){
				that.$data.memberUserData = result.data;
			},true)
		},
		//加载当前登录用户的信息
		loadUserInfo(){
			let that = this;
			this.iTools.request("Auth/me/userInfo",{},"GET",function(result){
				that.$data.userInfo = result.data;
			},true)
		},
		toLink(){
			uni.navigateTo({
				url:'/pages/myTeam/link'
			})
		}
    }
  }
</script>

<style>
.myTeam_mainBody{
	padding: 7px;
}
.myTeam_userInfo{
	border-radius: 7px;
	background-color: white;
	padding: 5px;
}
.userInfo{
	display: flex;
	align-items: center;
	border: 1px solid #cfcfcf;
	border-radius: 10px;
	
}
.user_headPortrait{
	width: 80px;
	height: 80px;
}
.user_headPortrait>image{
	margin-left: 7%;
}
.userInfo_detail{
	margin-left: 8%;
}
.userInfo_detail>.userInfo_detail_item{
	margin-bottom: 6px;
	font-size: 14px;
}
.userInfo_VipLevel>image{
	width: 40px;
	margin-top: 3px;
	margin-bottom: 55px;
	margin-left: 50px;
}

.myTeam_Btn{
	margin-top: 10px;
	display: flex;
	justify-content: space-between;
}
.myTeam_Btn>button{
	width: 48%;
	height: 40px;
	line-height: 40px;
	font-size: 14px;
	background-color: #30ABFF;
	color: white;
}

.myTeam_withdraw{
	padding: 5px;
	margin-top: 10px;
	height: 60px;
	border: 1px solid #cfcfcf;
	border-radius: 10px;
	display: flex;
	align-items: center;
	border-radius: 7px;
	background-color: white;
}
.myTeam_withdraw_item1{
	padding-left: 5%;
}
.myTeam_withdraw_item2{
	padding-left: 15%;
}
.myTeam_withdraw_item_detail{
	font-size: 11px;
	margin-bottom: 5px;
}

.myTeam_members{
	margin-top: 15px;
	height: 250px;
	border: 1px solid #cfcfcf;
	border-radius: 10px;
	border-radius: 7px;
	background-color: white;
}
.myTeam_members_header{
	width: 100%;
	display: flex;
	height: 45px;
	font-size: 14px;
	border-bottom: 1px solid #cfcfcf;
}
.myTeam_members_level{
	width: 50%;
	line-height: 45px;
	text-align: center;
}
.myTeam_members_level_choised{
	width: 50%;
	line-height: 45px;
	text-align: center;
	color: #30ABFF;
	border-bottom: 1px solid #30ABFF;
}
.myTeam_members_titles{
	display: flex;
	align-items: center;
	font-size: 12px;
	margin-top: 5px;
}
.members_title1{
	width: 30%;
	text-align: center;
	margin-bottom: 3px;
}
.members_title2{
	width: 16%;
	text-align: center;
	margin-bottom: 3px;
}
.members_title3{
	width: 16%;
	text-align: center;
	margin-bottom: 3px;
}
.members_title4{
	width: 38%;
	text-align: center;
	margin-bottom: 3px;
}
.members_info{
	display: flex;
	align-items: center;
	font-size: 12px;
	margin-top: 2px;
}
</style>
