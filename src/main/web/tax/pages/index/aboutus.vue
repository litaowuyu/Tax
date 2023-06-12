<template>
	<view class="">
		<uni-nav-bar  backgroundColor="#007fff" color="#ffffff">
			<view class="" slot="left" @click="toHome()">
				<uni-icons type="back"  size="25" color="#ffffff"></uni-icons>
			</view>
			<view class="register-title" slot="default" >About us</view>
		</uni-nav-bar>
	<view class="up-t1">
		<view class="up-t2">About Us</view>
	Tax Refund obtuvo una licencia comercial de Singapur en octubre de 2015 y lanzó oficialmente su aplicación de reembolso de impuestos en línea en mayo
 de 2021 para atender a los consumidores internacionales. En la actualidad, Sky Tax Refund ha brindado servicios de devolución de impuestos a 1.5 millones 
de consumidores en más de 30 países alrededor del mundo, brindando a más de 100,000 familias un ingreso de aproximadamente US$60 millones. 
Sky Tax Refund se enfoca en el mercado sudamericano a partir de diciembre de 2021.
	 Como parte importante de la economía mundial, el potencial económico de los 
	 países sudamericanos y el entusiasmo de la gente han dejado una profunda impresión en 
	 Sky Tax Refund. Sky Tax Refund planea servir y beneficiar a más de 30 millones de sudamericanos.
	</view>
	<view class="down-t1">
		<view class="down-t2">
			<text>Busness Iicense</text>
			<image src="/static/images/aboutus-pic/aboutus-pic1.jpg" mode="aspectFill"></image>
		</view>
	</view>
</view>
</template>

<script>
	export default {
		data(){
			return{
				
			}
		},
		methods:{
			toHome(){
				uni.switchTab({
					url:"/pages/index/index"
				});
			}
		}
	};
</script>

<style>
	page{
		background-color:#EBEDF0;
	}

.up-t1{
	background-color:#FFFFFF;
	margin: 8px;
	border-radius: 10px;
	padding: 5px;
	/* 行间距 */
	/* text-align: center; */
}
.up-t2{
	text-align: center;
	padding-top: 10px;
	padding-bottom: 5px;
}
.down-t1{
	background-color:#FFFFFF;
	margin: 8px;
	border-radius: 10px;
	height: 450px;
	padding: 10px;
}
.down-t2{
	text-align: center;
}
.down-t2>text{

}
.down-t2>image{
	margin-top: 10px;
	width: 370px;
	height: 400px;
	/* height: 510px; */
}
</style>
