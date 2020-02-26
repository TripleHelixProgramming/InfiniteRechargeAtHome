package frc.paths;

import com.team319.trajectory.Path;

public class GhostBiggie extends Path {
   // dt,x,y,left.pos,left.vel,left.acc,left.jerk,center.pos,center.vel,center.acc,center.jerk,right.pos,right.vel,right.acc,right.jerk,heading
	private static final double[][] points = {
				{0.0200,10.2528,-11.2000,0.0028,0.1400,7.0000,0.0000,0.0028,0.1400,7.0000,0.0000,0.0028,0.1400,7.0000,0.0000,0.0000},
				{0.0200,10.2584,-11.2000,0.0084,0.2800,7.0000,-0.0000,0.0084,0.2800,7.0000,0.0000,0.0084,0.2800,7.0000,-0.0000,0.0000},
				{0.0200,10.2668,-11.2000,0.0168,0.4200,7.0000,0.0000,0.0168,0.4200,7.0000,0.0000,0.0168,0.4200,7.0000,0.0000,0.0000},
				{0.0200,10.2780,-11.2000,0.0280,0.5600,7.0000,0.0000,0.0280,0.5600,7.0000,0.0000,0.0280,0.5600,7.0000,0.0000,0.0000},
				{0.0200,10.2920,-11.2000,0.0420,0.7000,7.0000,-0.0000,0.0420,0.7000,7.0000,0.0000,0.0420,0.7000,7.0000,-0.0000,0.0000},
				{0.0200,10.3088,-11.2000,0.0588,0.8400,7.0000,0.0000,0.0588,0.8400,7.0000,0.0000,0.0588,0.8400,7.0000,0.0000,0.0000},
				{0.0200,10.3284,-11.2000,0.0784,0.9800,7.0000,-0.0000,0.0784,0.9800,7.0000,0.0000,0.0784,0.9800,7.0000,-0.0000,0.0000},
				{0.0200,10.3508,-11.2000,0.1008,1.1200,7.0000,0.0000,0.1008,1.1200,7.0000,0.0000,0.1008,1.1200,7.0000,0.0000,0.0000},
				{0.0200,10.3760,-11.2000,0.1260,1.2600,7.0000,-0.0000,0.1260,1.2600,7.0000,0.0000,0.1260,1.2600,7.0000,-0.0000,0.0000},
				{0.0200,10.4040,-11.2000,0.1540,1.4000,7.0000,0.0000,0.1540,1.4000,7.0000,0.0000,0.1540,1.4000,7.0000,0.0000,0.0000},
				{0.0200,10.4348,-11.2000,0.1848,1.5400,7.0000,-0.0000,0.1848,1.5400,7.0000,0.0000,0.1848,1.5400,7.0000,-0.0000,0.0000},
				{0.0200,10.4684,-11.2000,0.2184,1.6800,7.0000,0.0000,0.2184,1.6800,7.0000,0.0000,0.2184,1.6800,7.0000,0.0000,0.0000},
				{0.0200,10.5048,-11.2000,0.2548,1.8200,7.0000,-0.0000,0.2548,1.8200,7.0000,0.0000,0.2548,1.8200,7.0000,-0.0000,0.0000},
				{0.0200,10.5440,-11.2000,0.2940,1.9600,7.0000,-0.0000,0.2940,1.9600,7.0000,0.0000,0.2940,1.9600,7.0000,-0.0000,0.0000},
				{0.0200,10.5860,-11.2000,0.3360,2.1000,7.0000,0.0000,0.3360,2.1000,7.0000,0.0000,0.3360,2.1000,7.0000,0.0000,0.0000},
				{0.0200,10.6308,-11.2000,0.3808,2.2400,7.0000,0.0000,0.3808,2.2400,7.0000,0.0000,0.3808,2.2400,7.0000,0.0000,0.0000},
				{0.0200,10.6784,-11.2000,0.4284,2.3800,7.0000,0.0000,0.4284,2.3800,7.0000,0.0000,0.4284,2.3800,7.0000,0.0000,0.0000},
				{0.0200,10.7288,-11.2000,0.4788,2.5200,7.0000,0.0000,0.4788,2.5200,7.0000,0.0000,0.4788,2.5200,7.0000,0.0000,0.0000},
				{0.0200,10.7820,-11.2000,0.5320,2.6600,7.0000,0.0000,0.5320,2.6600,7.0000,0.0000,0.5320,2.6600,7.0000,0.0000,0.0000},
				{0.0200,10.8380,-11.2000,0.5880,2.8000,7.0000,-0.0000,0.5880,2.8000,7.0000,0.0000,0.5880,2.8000,7.0000,-0.0000,0.0000},
				{0.0200,10.8968,-11.2000,0.6468,2.9400,7.0000,0.0000,0.6468,2.9400,7.0000,0.0000,0.6468,2.9400,7.0000,0.0000,0.0000},
				{0.0200,10.9584,-11.2000,0.7084,3.0800,7.0000,0.0000,0.7084,3.0800,7.0000,0.0000,0.7084,3.0800,7.0000,0.0000,0.0000},
				{0.0200,11.0228,-11.2000,0.7728,3.2200,7.0000,-0.0000,0.7728,3.2200,7.0000,0.0000,0.7728,3.2200,7.0000,-0.0000,0.0000},
				{0.0200,11.0900,-11.2000,0.8400,3.3600,7.0000,0.0000,0.8400,3.3600,7.0000,0.0000,0.8400,3.3600,7.0000,0.0000,0.0000},
				{0.0200,11.1600,-11.2000,0.9100,3.5000,7.0000,-0.0000,0.9100,3.5000,7.0000,0.0000,0.9100,3.5000,7.0000,-0.0000,0.0000},
				{0.0200,11.2328,-11.2000,0.9828,3.6400,7.0000,-0.0000,0.9828,3.6400,7.0000,0.0000,0.9828,3.6400,7.0000,-0.0000,0.0000},
				{0.0200,11.3084,-11.2000,1.0584,3.7800,7.0000,-0.0000,1.0584,3.7800,7.0000,0.0000,1.0584,3.7800,7.0000,-0.0000,0.0000},
				{0.0200,11.3868,-11.2000,1.1368,3.9200,7.0000,0.0000,1.1368,3.9200,7.0000,0.0000,1.1368,3.9200,7.0000,0.0000,0.0000},
				{0.0200,11.4680,-11.2000,1.2180,4.0600,7.0000,0.0000,1.2180,4.0600,7.0000,0.0000,1.2180,4.0600,7.0000,0.0000,0.0000},
				{0.0200,11.5520,-11.2000,1.3020,4.2000,7.0000,0.0000,1.3020,4.2000,7.0000,0.0000,1.3020,4.2000,7.0000,0.0000,0.0000},
				{0.0200,11.6388,-11.2000,1.3888,4.3400,7.0000,0.0000,1.3888,4.3400,7.0000,0.0000,1.3888,4.3400,7.0000,0.0000,0.0000},
				{0.0200,11.7284,-11.2000,1.4784,4.4800,7.0000,0.0000,1.4784,4.4800,7.0000,0.0000,1.4784,4.4800,7.0000,0.0000,0.0000},
				{0.0200,11.8208,-11.2000,1.5708,4.6200,7.0000,0.0000,1.5708,4.6200,7.0000,0.0000,1.5708,4.6200,7.0000,0.0000,0.0000},
				{0.0200,11.9160,-11.2000,1.6660,4.7600,7.0000,0.0000,1.6660,4.7600,7.0000,0.0000,1.6660,4.7600,7.0000,0.0000,0.0000},
				{0.0200,12.0140,-11.2000,1.7640,4.9000,7.0000,-0.0000,1.7640,4.9000,7.0000,0.0000,1.7640,4.9000,7.0000,-0.0000,0.0000},
				{0.0200,12.1148,-11.2000,1.8648,5.0400,7.0000,0.0000,1.8648,5.0400,7.0000,0.0000,1.8648,5.0400,7.0000,0.0000,0.0000},
				{0.0200,12.2184,-11.2000,1.9684,5.1800,7.0000,-0.0000,1.9684,5.1800,7.0000,0.0000,1.9684,5.1800,7.0000,-0.0000,0.0000},
				{0.0200,12.3248,-11.2000,2.0748,5.3200,7.0000,0.0000,2.0748,5.3200,7.0000,0.0000,2.0748,5.3200,7.0000,0.0000,0.0000},
				{0.0200,12.4340,-11.2000,2.1840,5.4600,7.0000,-0.0000,2.1840,5.4600,7.0000,0.0000,2.1840,5.4600,7.0000,-0.0000,0.0000},
				{0.0200,12.5460,-11.2000,2.2960,5.6000,7.0000,0.0000,2.2960,5.6000,7.0000,0.0000,2.2960,5.6000,7.0000,0.0000,0.0000},
				{0.0200,12.6608,-11.2000,2.4108,5.7400,7.0000,-0.0000,2.4108,5.7400,7.0000,0.0000,2.4108,5.7400,7.0000,-0.0000,0.0000},
				{0.0200,12.7784,-11.2000,2.5284,5.8800,7.0000,0.0000,2.5284,5.8800,7.0000,0.0000,2.5284,5.8800,7.0000,0.0000,0.0000},
				{0.0200,12.8988,-11.2000,2.6488,6.0200,7.0000,-0.0000,2.6488,6.0200,7.0000,0.0000,2.6488,6.0200,7.0000,-0.0000,0.0000},
				{0.0200,13.0220,-11.2000,2.7720,6.1600,7.0000,0.0000,2.7720,6.1600,7.0000,0.0000,2.7720,6.1600,7.0000,0.0000,0.0000},
				{0.0200,13.1480,-11.2000,2.8980,6.3000,7.0000,-0.0000,2.8980,6.3000,7.0000,0.0000,2.8980,6.3000,7.0000,-0.0000,0.0000},
				{0.0200,13.2768,-11.2000,3.0268,6.4400,7.0000,0.0000,3.0268,6.4400,7.0000,0.0000,3.0268,6.4400,7.0000,0.0000,0.0000},
				{0.0200,13.4084,-11.2000,3.1584,6.5800,7.0000,-0.0000,3.1584,6.5800,7.0000,0.0000,3.1584,6.5800,7.0000,-0.0000,0.0000},
				{0.0200,13.5428,-11.2000,3.2928,6.7200,7.0000,0.0000,3.2928,6.7200,7.0000,0.0000,3.2928,6.7200,7.0000,0.0000,0.0000},
				{0.0200,13.6800,-11.2000,3.4300,6.8600,7.0000,0.0000,3.4300,6.8600,7.0000,0.0000,3.4300,6.8600,7.0000,0.0000,0.0000},
				{0.0200,13.8200,-11.2000,3.5700,7.0000,7.0000,-0.0000,3.5700,7.0000,7.0000,0.0000,3.5700,7.0000,7.0000,-0.0000,0.0000},
				{0.0200,13.9600,-11.2000,3.7100,7.0000,0.0000,-350.0000,3.7100,7.0000,7.0000,0.0000,3.7100,7.0000,0.0000,-350.0000,0.0000},
				{0.0200,14.1000,-11.2000,3.8500,7.0000,0.0000,0.0000,3.8500,7.0000,7.0000,0.0000,3.8500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,14.2400,-11.2000,3.9900,7.0000,0.0000,0.0000,3.9900,7.0000,7.0000,0.0000,3.9900,7.0000,0.0000,0.0000,0.0000},
				{0.0200,14.3800,-11.2000,4.1300,7.0000,0.0000,-0.0000,4.1300,7.0000,7.0000,0.0000,4.1300,7.0000,0.0000,-0.0000,0.0000},
				{0.0200,14.5200,-11.2000,4.2700,7.0000,-0.0000,-0.0000,4.2700,7.0000,7.0000,0.0000,4.2700,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,14.6600,-11.2000,4.4100,7.0000,0.0000,0.0000,4.4100,7.0000,7.0000,0.0000,4.4100,7.0000,0.0000,0.0000,0.0000},
				{0.0200,14.8000,-11.2000,4.5500,7.0000,-0.0000,-0.0000,4.5500,7.0000,7.0000,0.0000,4.5500,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,14.9400,-11.2000,4.6900,7.0000,0.0000,0.0000,4.6900,7.0000,7.0000,0.0000,4.6900,7.0000,0.0000,0.0000,0.0000},
				{0.0200,15.0800,-11.2000,4.8300,7.0000,-0.0000,-0.0000,4.8300,7.0000,7.0000,0.0000,4.8300,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,15.2200,-11.2000,4.9700,7.0000,0.0000,0.0000,4.9700,7.0000,7.0000,0.0000,4.9700,7.0000,0.0000,0.0000,0.0000},
				{0.0200,15.3600,-11.2000,5.1100,7.0000,0.0000,0.0000,5.1100,7.0000,7.0000,0.0000,5.1100,7.0000,0.0000,0.0000,0.0000},
				{0.0200,15.5000,-11.2000,5.2500,7.0000,0.0000,0.0000,5.2500,7.0000,7.0000,0.0000,5.2500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,15.6400,-11.2000,5.3900,7.0000,0.0000,0.0000,5.3900,7.0000,7.0000,0.0000,5.3900,7.0000,0.0000,0.0000,0.0000},
				{0.0200,15.7800,-11.2000,5.5300,7.0000,-0.0000,-0.0000,5.5300,7.0000,7.0000,0.0000,5.5300,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,15.9200,-11.2000,5.6700,7.0000,0.0000,0.0000,5.6700,7.0000,7.0000,0.0000,5.6700,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.0600,-11.2000,5.8100,7.0000,0.0000,0.0000,5.8100,7.0000,7.0000,0.0000,5.8100,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.2000,-11.2000,5.9500,7.0000,-0.0000,-0.0000,5.9500,7.0000,7.0000,0.0000,5.9500,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,16.3400,-11.2000,6.0900,7.0000,0.0000,0.0000,6.0900,7.0000,7.0000,0.0000,6.0900,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.4800,-11.2000,6.2300,7.0000,0.0000,0.0000,6.2300,7.0000,7.0000,0.0000,6.2300,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.6200,-11.2000,6.3700,7.0000,0.0000,0.0000,6.3700,7.0000,7.0000,0.0000,6.3700,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.7600,-11.2000,6.5100,7.0000,0.0000,0.0000,6.5100,7.0000,7.0000,0.0000,6.5100,7.0000,0.0000,0.0000,0.0000},
				{0.0200,16.9000,-11.2000,6.6500,7.0000,0.0000,0.0000,6.6500,7.0000,7.0000,0.0000,6.6500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.0400,-11.2000,6.7900,7.0000,0.0000,0.0000,6.7900,7.0000,7.0000,0.0000,6.7900,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.1800,-11.2000,6.9300,7.0000,0.0000,0.0000,6.9300,7.0000,7.0000,0.0000,6.9300,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.3200,-11.2000,7.0700,7.0000,0.0000,0.0000,7.0700,7.0000,7.0000,0.0000,7.0700,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.4600,-11.2000,7.2100,7.0000,0.0000,0.0000,7.2100,7.0000,7.0000,0.0000,7.2100,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.6000,-11.2000,7.3500,7.0000,0.0000,0.0000,7.3500,7.0000,7.0000,0.0000,7.3500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,17.7400,-11.2000,7.4900,7.0000,-0.0000,-0.0000,7.4900,7.0000,7.0000,0.0000,7.4900,7.0000,-0.0000,-0.0000,0.0000},
				{0.0200,17.8800,-11.2000,7.6300,7.0000,0.0000,0.0000,7.6300,7.0000,7.0000,0.0000,7.6300,7.0000,0.0000,0.0000,0.0000},
				{0.0200,18.0200,-11.2000,7.7700,7.0004,0.0201,1.0042,7.7700,7.0000,7.0000,0.0000,7.7700,6.9996,-0.0201,-1.0042,-0.0000},
				{0.0200,18.1600,-11.2000,7.9105,7.0223,1.0936,53.6782,7.9100,7.0000,7.0000,0.0000,7.9095,6.9777,-1.0937,-53.6784,-0.0004},
				{0.0200,18.3000,-11.2001,8.0514,7.0468,1.2247,6.5507,8.0500,7.0000,7.0000,0.0000,8.0486,6.9532,-1.2247,-6.5512,-0.0012},
				{0.0200,18.4400,-11.2004,8.1926,7.0587,0.5945,-31.5106,8.1900,7.0000,7.0000,0.0000,8.1874,6.9413,-0.5945,31.5108,-0.0023},
				{0.0200,18.5800,-11.2008,8.3337,7.0590,0.0149,-28.9785,8.3300,7.0000,7.0000,0.0000,8.3263,6.9410,-0.0149,28.9790,-0.0033},
				{0.0200,18.7200,-11.2013,8.4747,7.0487,-0.5139,-26.4399,8.4700,7.0000,7.0000,0.0000,8.4653,6.9513,0.5139,26.4405,-0.0042},
				{0.0200,18.8600,-11.2019,8.6153,7.0288,-0.9922,-23.9128,8.6100,7.0000,7.0000,0.0000,8.6047,6.9712,0.9922,23.9130,-0.0047},
				{0.0200,19.0000,-11.2026,8.7553,7.0004,-1.4204,-21.4101,8.7500,7.0000,7.0000,0.0000,8.7447,6.9996,1.4204,21.4098,-0.0047},
				{0.0200,19.1400,-11.2032,8.8946,6.9644,-1.7992,-18.9400,8.8900,7.0000,7.0000,0.0000,8.8854,7.0356,1.7992,18.9391,-0.0041},
				{0.0200,19.2800,-11.2037,9.0330,6.9219,-2.1293,-16.5050,9.0300,7.0000,7.0000,0.0000,9.0270,7.0781,2.1292,16.5033,-0.0027},
				{0.0200,19.4200,-11.2039,9.1705,6.8736,-2.4113,-14.1016,9.1700,7.0000,7.0000,0.0000,9.1695,7.1264,2.4112,14.0992,-0.0004},
				{0.0200,19.5600,-11.2038,9.3069,6.8207,-2.6457,-11.7205,9.3100,7.0000,7.0000,0.0000,9.3131,7.1793,2.6456,11.7175,0.0027},
				{0.0200,19.6972,-11.2031,9.4395,6.6294,-9.5680,-346.1128,9.4472,6.8600,-7.0000,0.0000,9.4549,7.0906,-4.4322,-353.8890,0.0068},
				{0.0200,19.8316,-11.2019,9.5683,6.4388,-9.5294,1.9275,9.5816,6.7200,-7.0000,0.0000,9.5949,7.0012,-4.4708,-1.9293,0.0118},
				{0.0200,19.9632,-11.2000,9.6933,6.2500,-9.4384,4.5490,9.7132,6.5800,-7.0000,0.0000,9.7331,6.9100,-4.5618,-4.5502,0.0177},
				{0.0200,20.0919,-11.1973,9.8146,6.0639,-9.3034,6.7544,9.8420,6.4400,-7.0000,0.0000,9.8694,6.8160,-4.6969,-6.7550,0.0244},
				{0.0200,20.2179,-11.1937,9.9322,5.8813,-9.1317,8.5837,9.9680,6.3000,-7.0000,0.0000,10.0038,6.7187,-4.8686,-8.5837,0.0318},
				{0.0200,20.3410,-11.1893,10.0462,5.7027,-8.9303,10.0689,10.0912,6.1600,-7.0000,0.0000,10.1362,6.6173,-5.0699,-10.0683,0.0400},
				{0.0200,20.4613,-11.1840,10.1568,5.5286,-8.7056,11.2355,10.2116,6.0200,-7.0000,0.0000,10.2664,6.5114,-5.2946,-11.2343,0.0487},
				{0.0200,20.5787,-11.1777,10.2640,5.3593,-8.4635,12.1037,10.3292,5.8800,-7.0000,0.0000,10.3944,6.4006,-5.5367,-12.1021,0.0580},
				{0.0200,20.6933,-11.1705,10.3679,5.1951,-8.2097,12.6909,10.4440,5.7400,-7.0000,0.0000,10.5201,6.2848,-5.7904,-12.6889,0.0676},
				{0.0200,20.8050,-11.1624,10.4686,5.0361,-7.9494,13.0133,10.5560,5.6000,-7.0000,0.0000,10.6434,6.1638,-6.0507,-13.0110,0.0777},
				{0.0200,20.9138,-11.1534,10.5663,4.8824,-7.6877,13.0871,10.6652,5.4600,-7.0000,0.0000,10.7641,6.0376,-6.3124,-13.0848,0.0879},
				{0.0200,21.0198,-11.1435,10.6609,4.7338,-7.4291,12.9306,10.7716,5.3200,-7.0000,0.0000,10.8823,5.9062,-6.5709,-12.9283,0.0984},
				{0.0200,21.1228,-11.1328,10.7527,4.5902,-7.1778,12.5640,10.8752,5.1800,-7.0000,0.0000,10.9976,5.7697,-6.8222,-12.5618,0.1088},
				{0.0200,21.2230,-11.1213,10.8418,4.4515,-6.9376,12.0104,10.9760,5.0400,-7.0000,0.0000,11.1102,5.6285,-7.0623,-12.0085,0.1193},
				{0.0200,21.3202,-11.1091,10.9281,4.3173,-6.7117,11.2954,11.0740,4.9000,-7.0000,0.0000,11.2199,5.4827,-7.2882,-11.2937,0.1297},
				{0.0200,21.4145,-11.0963,11.0119,4.1872,-6.5028,10.4462,11.1692,4.7600,-7.0000,0.0000,11.3265,5.3328,-7.4971,-10.4449,0.1399},
				{0.0200,21.5060,-11.0830,11.0931,4.0609,-6.3129,9.4914,11.2616,4.6200,-7.0000,0.0000,11.4301,5.1790,-7.6869,-9.4903,0.1498},
				{0.0200,21.5945,-11.0692,11.1718,3.9381,-6.1437,8.4593,11.3512,4.4800,-7.0000,0.0000,11.5305,5.0219,-7.8561,-8.4586,0.1594},
				{0.0200,21.6801,-11.0550,11.2482,3.8181,-5.9962,7.3777,11.4380,4.3400,-7.0000,0.0000,11.6278,4.8618,-8.0036,-7.3774,0.1687},
				{0.0200,21.7629,-11.0405,11.3222,3.7007,-5.8707,6.2724,11.5220,4.2000,-7.0000,0.0000,11.7218,4.6992,-8.1291,-6.2723,0.1776},
				{0.0200,21.8427,-11.0259,11.3939,3.5854,-5.7674,5.1667,11.6032,4.0600,-7.0000,0.0000,11.8125,4.5346,-8.2324,-5.1669,0.1860},
				{0.0200,21.9197,-11.0111,11.4634,3.4717,-5.6858,4.0810,11.6816,3.9200,-7.0000,0.0000,11.8998,4.3683,-8.3140,-4.0813,0.1940},
				{0.0200,21.9939,-10.9962,11.5305,3.3592,-5.6251,3.0323,11.7572,3.7800,-7.0000,0.0000,11.9838,4.2008,-8.3747,-3.0328,0.2015},
				{0.0200,22.0651,-10.9814,11.5955,3.2475,-5.5845,2.0345,11.8300,3.6400,-7.0000,0.0000,12.0645,4.0325,-8.4154,-2.0352,0.2084},
				{0.0200,22.1336,-10.9667,11.6582,3.1362,-5.5625,1.0983,11.9000,3.5000,-7.0000,0.0000,12.1418,3.8638,-8.4374,-1.0990,0.2149},
				{0.0200,22.1992,-10.9522,11.7187,3.0251,-5.5579,0.2314,11.9672,3.3600,-7.0000,0.0000,12.2157,3.6949,-8.4420,-0.2321,0.2209},
				{0.0200,22.2620,-10.9379,11.7770,2.9137,-5.5691,-0.5610,12.0316,3.2200,-7.0000,0.0000,12.2862,3.5263,-8.4308,0.5603,0.2263},
				{0.0200,22.3220,-10.9239,11.8330,2.8018,-5.5946,-1.2758,12.0932,3.0800,-7.0000,0.0000,12.3534,3.3582,-8.4053,1.2750,0.2313},
				{0.0200,22.3792,-10.9103,11.8868,2.6891,-5.6328,-1.9114,12.1520,2.9400,-7.0000,0.0000,12.4172,3.1909,-8.3671,1.9108,0.2357},
				{0.0200,22.4336,-10.8971,11.9383,2.5755,-5.6822,-2.4679,12.2080,2.8000,-7.0000,0.0000,12.4777,3.0245,-8.3178,2.4673,0.2397},
				{0.0200,22.4853,-10.8844,11.9875,2.4607,-5.7411,-2.9461,12.2612,2.6600,-7.0000,0.0000,12.5348,2.8593,-8.2589,2.9455,0.2433},
				{0.0200,22.5342,-10.8722,12.0344,2.3445,-5.8081,-3.3474,12.3116,2.5200,-7.0000,0.0000,12.5888,2.6955,-8.1919,3.3470,0.2464},
				{0.0200,22.5803,-10.8605,12.0790,2.2269,-5.8815,-3.6739,12.3592,2.3800,-7.0000,0.0000,12.6394,2.5331,-8.1184,3.6735,0.2491},
				{0.0200,22.6237,-10.8494,12.1211,2.1077,-5.9601,-3.9277,12.4040,2.2400,-7.0000,0.0000,12.6869,2.3723,-8.0399,3.9273,0.2514},
				{0.0200,22.6644,-10.8389,12.1609,1.9868,-6.0423,-4.1112,12.4460,2.1000,-7.0000,0.0000,12.7311,2.2132,-7.9577,4.1109,0.2535},
				{0.0200,22.7023,-10.8290,12.1981,1.8643,-6.1268,-4.2268,12.4852,1.9600,-7.0000,0.0000,12.7722,2.0557,-7.8731,4.2266,0.2552},
				{0.0200,22.7375,-10.8198,12.2329,1.7400,-6.2124,-4.2772,12.5216,1.8200,-7.0000,0.0000,12.8102,1.9000,-7.7876,4.2771,0.2566},
				{0.0200,22.7700,-10.8113,12.2652,1.6141,-6.2977,-4.2650,12.5552,1.6800,-7.0000,0.0000,12.8452,1.7459,-7.7023,4.2649,0.2578},
				{0.0200,22.7998,-10.8034,12.2949,1.4865,-6.3815,-4.1929,12.5860,1.5400,-7.0000,0.0000,12.8770,1.5935,-7.6185,4.1928,0.2587},
				{0.0200,22.8269,-10.7962,12.3221,1.3572,-6.4628,-4.0639,12.6140,1.4000,-7.0000,0.0000,12.9059,1.4428,-7.5372,4.0638,0.2595},
				{0.0200,22.8512,-10.7898,12.3466,1.2264,-6.5404,-3.8811,12.6392,1.2600,-7.0000,0.0000,12.9318,1.2936,-7.4596,3.8810,0.2601},
				{0.0200,22.8729,-10.7840,12.3685,1.0941,-6.6134,-3.6478,12.6616,1.1200,-7.0000,0.0000,12.9547,1.1459,-7.3866,3.6478,0.2605},
				{0.0200,22.8918,-10.7790,12.3877,0.9605,-6.6808,-3.3680,12.6812,0.9800,-7.0000,0.0000,12.9747,0.9995,-7.3192,3.3679,0.2609},
				{0.0200,22.9080,-10.7746,12.4042,0.8257,-6.7417,-3.0454,12.6980,0.8400,-7.0000,0.0000,12.9918,0.8543,-7.2583,3.0454,0.2611},
				{0.0200,22.9216,-10.7710,12.4180,0.6898,-6.7954,-2.6846,12.7120,0.7000,-7.0000,0.0000,13.0060,0.7102,-7.2046,2.6846,0.2613},
				{0.0200,22.9324,-10.7681,12.4291,0.5529,-6.8412,-2.2902,12.7232,0.5600,-7.0000,0.0000,13.0173,0.5671,-7.1588,2.2902,0.2614},
				{0.0200,22.9405,-10.7659,12.4374,0.4154,-6.8785,-1.8673,12.7316,0.4200,-7.0000,0.0000,13.0258,0.4246,-7.1215,1.8673,0.2615},
				{0.0200,22.9459,-10.7645,12.4429,0.2772,-6.9069,-1.4211,12.7372,0.2800,-7.0000,0.0000,13.0314,0.2828,-7.0931,1.4211,0.2616},
				{0.0200,22.9486,-10.7638,12.4457,0.1387,-6.9261,-0.9573,12.7400,0.1400,-7.0000,0.0000,13.0343,0.1413,-7.0739,0.9573,0.2616},
				{0.0200,22.9486,-10.7638,12.4457,0.0000,-6.9357,-0.4816,12.7400,0.0000,-7.0000,0.0000,13.0343,0.0000,-7.0643,0.4816,0.2616},
				{0.0200,22.9459,-10.7645,12.4485,0.1387,6.9357,693.5712,12.7372,-0.1400,-7.0000,0.0000,13.0371,0.1413,7.0643,706.4288,0.2616},

	    };

	@Override
	public double[][] getPath() {
	    return points;
	}
}