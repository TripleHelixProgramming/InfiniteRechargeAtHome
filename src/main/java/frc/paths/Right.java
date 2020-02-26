package frc.paths;

import com.team319.trajectory.Path;

public class Right extends Path {
   // dt,x,y,left.pos,left.vel,left.acc,left.jerk,center.pos,center.vel,center.acc,center.jerk,right.pos,right.vel,right.acc,right.jerk,heading
	private static final double[][] points = {
				{0.0200,23.0028,-11.2000,0.0028,0.1400,7.0000,0.0000,0.0028,0.1400,7.0000,0.0000,0.0028,0.1400,7.0000,0.0000,0.0000},
				{0.0200,23.0084,-11.2000,0.0084,0.2800,7.0000,0.0000,0.0084,0.2800,7.0000,0.0000,0.0084,0.2800,7.0000,0.0000,0.0000},
				{0.0200,23.0168,-11.2000,0.0168,0.4200,7.0000,-0.0000,0.0168,0.4200,7.0000,0.0000,0.0168,0.4200,7.0000,-0.0000,0.0000},
				{0.0200,23.0280,-11.2000,0.0280,0.5600,7.0000,0.0000,0.0280,0.5600,7.0000,0.0000,0.0280,0.5600,7.0000,0.0000,0.0000},
				{0.0200,23.0420,-11.2000,0.0420,0.7000,7.0000,0.0000,0.0420,0.7000,7.0000,0.0000,0.0420,0.7000,7.0000,0.0000,0.0000},
				{0.0200,23.0588,-11.2000,0.0588,0.8400,7.0000,-0.0000,0.0588,0.8400,7.0000,0.0000,0.0588,0.8400,7.0000,-0.0000,0.0000},
				{0.0200,23.0784,-11.2000,0.0784,0.9800,7.0000,0.0000,0.0784,0.9800,7.0000,0.0000,0.0784,0.9800,7.0000,0.0000,0.0000},
				{0.0200,23.1008,-11.2000,0.1008,1.1200,7.0000,0.0000,0.1008,1.1200,7.0000,0.0000,0.1008,1.1200,7.0000,0.0000,0.0000},
				{0.0200,23.1260,-11.2000,0.1260,1.2600,7.0000,0.0000,0.1260,1.2600,7.0000,0.0000,0.1260,1.2600,7.0000,0.0000,0.0000},
				{0.0200,23.1540,-11.2000,0.1540,1.4000,7.0000,-0.0000,0.1540,1.4000,7.0000,0.0000,0.1540,1.4000,7.0000,-0.0000,0.0000},
				{0.0200,23.1848,-11.2000,0.1848,1.5400,7.0000,0.0000,0.1848,1.5400,7.0000,0.0000,0.1848,1.5400,7.0000,0.0000,0.0000},
				{0.0200,23.2184,-11.2000,0.2184,1.6800,7.0000,-0.0000,0.2184,1.6800,7.0000,0.0000,0.2184,1.6800,7.0000,-0.0000,0.0000},
				{0.0200,23.2548,-11.2000,0.2548,1.8200,7.0000,0.0000,0.2548,1.8200,7.0000,0.0000,0.2548,1.8200,7.0000,0.0000,0.0000},
				{0.0200,23.2940,-11.2000,0.2940,1.9600,7.0000,-0.0000,0.2940,1.9600,7.0000,0.0000,0.2940,1.9600,7.0000,-0.0000,0.0000},
				{0.0200,23.3360,-11.2000,0.3360,2.1000,7.0000,0.0000,0.3360,2.1000,7.0000,0.0000,0.3360,2.1000,7.0000,0.0000,0.0000},
				{0.0200,23.3808,-11.2000,0.3808,2.2400,7.0000,0.0000,0.3808,2.2400,7.0000,0.0000,0.3808,2.2400,7.0000,0.0000,0.0000},
				{0.0200,23.4284,-11.2000,0.4284,2.3800,7.0000,0.0000,0.4284,2.3800,7.0000,0.0000,0.4284,2.3800,7.0000,0.0000,0.0000},
				{0.0200,23.4788,-11.2000,0.4788,2.5200,7.0000,0.0000,0.4788,2.5200,7.0000,0.0000,0.4788,2.5200,7.0000,0.0000,0.0000},
				{0.0200,23.5320,-11.2000,0.5320,2.6600,7.0000,0.0000,0.5320,2.6600,7.0000,0.0000,0.5320,2.6600,7.0000,0.0000,0.0000},
				{0.0200,23.5880,-11.2000,0.5880,2.8000,7.0000,-0.0000,0.5880,2.8000,7.0000,0.0000,0.5880,2.8000,7.0000,-0.0000,0.0000},
				{0.0200,23.6468,-11.2000,0.6468,2.9400,7.0000,-0.0000,0.6468,2.9400,7.0000,0.0000,0.6468,2.9400,7.0000,-0.0000,0.0000},
				{0.0200,23.7084,-11.2000,0.7084,3.0800,7.0000,-0.0000,0.7084,3.0800,7.0000,0.0000,0.7084,3.0800,7.0000,-0.0000,0.0000},
				{0.0200,23.7728,-11.2000,0.7728,3.2200,7.0000,0.0000,0.7728,3.2200,7.0000,0.0000,0.7728,3.2200,7.0000,0.0000,0.0000},
				{0.0200,23.8400,-11.2000,0.8400,3.3600,7.0000,-0.0000,0.8400,3.3600,7.0000,0.0000,0.8400,3.3600,7.0000,-0.0000,0.0000},
				{0.0200,23.9100,-11.2000,0.9100,3.5000,7.0000,0.0000,0.9100,3.5000,7.0000,0.0000,0.9100,3.5000,7.0000,0.0000,0.0000},
				{0.0200,23.9828,-11.2000,0.9828,3.6400,7.0000,-0.0000,0.9828,3.6400,7.0000,0.0000,0.9828,3.6400,7.0000,-0.0000,0.0000},
				{0.0200,24.0584,-11.2000,1.0584,3.7800,7.0000,-0.0000,1.0584,3.7800,7.0000,0.0000,1.0584,3.7800,7.0000,-0.0000,0.0000},
				{0.0200,24.1368,-11.2000,1.1368,3.9200,7.0000,0.0000,1.1368,3.9200,7.0000,0.0000,1.1368,3.9200,7.0000,0.0000,0.0000},
				{0.0200,24.2180,-11.2000,1.2180,4.0600,7.0000,0.0000,1.2180,4.0600,7.0000,0.0000,1.2180,4.0600,7.0000,0.0000,0.0000},
				{0.0200,24.3020,-11.2000,1.3020,4.2000,7.0000,0.0000,1.3020,4.2000,7.0000,0.0000,1.3020,4.2000,7.0000,0.0000,0.0000},
				{0.0200,24.3888,-11.2000,1.3888,4.3400,7.0000,-0.0000,1.3888,4.3400,7.0000,0.0000,1.3888,4.3400,7.0000,-0.0000,0.0000},
				{0.0200,24.4784,-11.2000,1.4784,4.4800,7.0000,0.0000,1.4784,4.4800,7.0000,0.0000,1.4784,4.4800,7.0000,0.0000,0.0000},
				{0.0200,24.5708,-11.2000,1.5708,4.6200,7.0000,-0.0000,1.5708,4.6200,7.0000,0.0000,1.5708,4.6200,7.0000,-0.0000,0.0000},
				{0.0200,24.6660,-11.2000,1.6660,4.7600,7.0000,-0.0000,1.6660,4.7600,7.0000,0.0000,1.6660,4.7600,7.0000,-0.0000,0.0000},
				{0.0200,24.7640,-11.2000,1.7640,4.9000,7.0000,0.0000,1.7640,4.9000,7.0000,0.0000,1.7640,4.9000,7.0000,0.0000,0.0000},
				{0.0200,24.8648,-11.2000,1.8648,5.0400,7.0000,-0.0000,1.8648,5.0400,7.0000,0.0000,1.8648,5.0400,7.0000,-0.0000,0.0000},
				{0.0200,24.9684,-11.2000,1.9684,5.1800,7.0000,-0.0000,1.9684,5.1800,7.0000,0.0000,1.9684,5.1800,7.0000,-0.0000,0.0000},
				{0.0200,25.0748,-11.2000,2.0748,5.3200,7.0000,0.0000,2.0748,5.3200,7.0000,0.0000,2.0748,5.3200,7.0000,0.0000,0.0000},
				{0.0200,25.1840,-11.2000,2.1840,5.4600,7.0000,-0.0000,2.1840,5.4600,7.0000,0.0000,2.1840,5.4600,7.0000,-0.0000,0.0000},
				{0.0200,25.2960,-11.2000,2.2960,5.6000,7.0000,0.0000,2.2960,5.6000,7.0000,0.0000,2.2960,5.6000,7.0000,0.0000,0.0000},
				{0.0200,25.4108,-11.2000,2.4108,5.7400,7.0000,0.0000,2.4108,5.7400,7.0000,0.0000,2.4108,5.7400,7.0000,0.0000,0.0000},
				{0.0200,25.5284,-11.2000,2.5284,5.8800,7.0000,0.0000,2.5284,5.8800,7.0000,0.0000,2.5284,5.8800,7.0000,0.0000,0.0000},
				{0.0200,25.6488,-11.2000,2.6488,6.0200,7.0000,0.0000,2.6488,6.0200,7.0000,0.0000,2.6488,6.0200,7.0000,0.0000,0.0000},
				{0.0200,25.7720,-11.2000,2.7720,6.1600,7.0000,0.0000,2.7720,6.1600,7.0000,0.0000,2.7720,6.1600,7.0000,0.0000,0.0000},
				{0.0200,25.8980,-11.2000,2.8980,6.3000,7.0000,0.0000,2.8980,6.3000,7.0000,0.0000,2.8980,6.3000,7.0000,0.0000,0.0000},
				{0.0200,26.0268,-11.2000,3.0268,6.4400,7.0000,0.0000,3.0268,6.4400,7.0000,0.0000,3.0268,6.4400,7.0000,0.0000,0.0000},
				{0.0200,26.1584,-11.2000,3.1584,6.5800,7.0000,-0.0000,3.1584,6.5800,7.0000,0.0000,3.1584,6.5800,7.0000,-0.0000,0.0000},
				{0.0200,26.2928,-11.2000,3.2928,6.7200,7.0000,0.0000,3.2928,6.7200,7.0000,0.0000,3.2928,6.7200,7.0000,0.0000,0.0000},
				{0.0200,26.4300,-11.2000,3.4300,6.8600,7.0000,0.0000,3.4300,6.8600,7.0000,0.0000,3.4300,6.8600,7.0000,0.0000,0.0000},
				{0.0200,26.5700,-11.2000,3.5700,7.0000,7.0000,-0.0000,3.5700,7.0000,7.0000,0.0000,3.5700,7.0000,7.0000,-0.0000,0.0000},
				{0.0200,26.7100,-11.2000,3.7100,7.0000,0.0000,-350.0000,3.7100,7.0000,7.0000,0.0000,3.7100,7.0000,0.0000,-350.0000,0.0000},
				{0.0200,26.8500,-11.2000,3.8500,7.0000,0.0000,0.0000,3.8500,7.0000,7.0000,0.0000,3.8500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,26.9900,-11.2000,3.9900,7.0000,0.0000,-0.0000,3.9900,7.0000,7.0000,0.0000,3.9900,7.0000,0.0000,-0.0000,0.0000},
				{0.0200,27.1300,-11.2000,4.1300,7.0000,0.0000,0.0000,4.1300,7.0000,7.0000,0.0000,4.1300,7.0000,0.0000,0.0000,0.0000},
				{0.0200,27.2700,-11.2000,4.2700,7.0000,0.0000,-0.0000,4.2700,7.0000,7.0000,0.0000,4.2700,7.0000,0.0000,-0.0000,0.0000},
				{0.0200,27.4100,-11.2000,4.4100,7.0000,0.0000,-0.0000,4.4100,7.0000,7.0000,0.0000,4.4100,7.0000,0.0000,-0.0000,0.0000},
				{0.0200,27.5500,-11.2000,4.5500,7.0000,0.0000,0.0000,4.5500,7.0000,7.0000,0.0000,4.5500,7.0000,0.0000,0.0000,0.0000},
				{0.0200,27.6872,-11.2000,4.6872,6.8600,-7.0000,-350.0000,4.6872,6.8600,-7.0000,0.0000,4.6872,6.8600,-7.0000,-350.0000,0.0000},
				{0.0200,27.8216,-11.2000,4.8216,6.7200,-7.0000,-0.0000,4.8216,6.7200,-7.0000,0.0000,4.8216,6.7200,-7.0000,-0.0000,0.0000},
				{0.0200,27.9532,-11.2000,4.9532,6.5800,-7.0000,0.0000,4.9532,6.5800,-7.0000,0.0000,4.9532,6.5800,-7.0000,0.0000,0.0000},
				{0.0200,28.0820,-11.2000,5.0820,6.4400,-7.0000,-0.0000,5.0820,6.4400,-7.0000,0.0000,5.0820,6.4400,-7.0000,-0.0000,0.0000},
				{0.0200,28.2080,-11.2000,5.2080,6.3000,-7.0000,0.0000,5.2080,6.3000,-7.0000,0.0000,5.2080,6.3000,-7.0000,0.0000,0.0000},
				{0.0200,28.3312,-11.2000,5.3312,6.1600,-7.0000,0.0000,5.3312,6.1600,-7.0000,0.0000,5.3312,6.1600,-7.0000,0.0000,0.0000},
				{0.0200,28.4516,-11.2000,5.4516,6.0200,-7.0000,-0.0000,5.4516,6.0200,-7.0000,0.0000,5.4516,6.0200,-7.0000,-0.0000,0.0000},
				{0.0200,28.5692,-11.2000,5.5692,5.8800,-7.0000,0.0000,5.5692,5.8800,-7.0000,0.0000,5.5692,5.8800,-7.0000,0.0000,0.0000},
				{0.0200,28.6840,-11.2000,5.6840,5.7400,-7.0000,-0.0000,5.6840,5.7400,-7.0000,0.0000,5.6840,5.7400,-7.0000,-0.0000,0.0000},
				{0.0200,28.7960,-11.2000,5.7960,5.6000,-7.0000,0.0000,5.7960,5.6000,-7.0000,0.0000,5.7960,5.6000,-7.0000,0.0000,0.0000},
				{0.0200,28.9052,-11.2000,5.9052,5.4600,-7.0000,0.0000,5.9052,5.4600,-7.0000,0.0000,5.9052,5.4600,-7.0000,0.0000,0.0000},
				{0.0200,29.0116,-11.2000,6.0116,5.3200,-7.0000,-0.0000,6.0116,5.3200,-7.0000,0.0000,6.0116,5.3200,-7.0000,-0.0000,0.0000},
				{0.0200,29.1152,-11.2000,6.1152,5.1800,-7.0000,0.0000,6.1152,5.1800,-7.0000,0.0000,6.1152,5.1800,-7.0000,0.0000,0.0000},
				{0.0200,29.2160,-11.2000,6.2160,5.0400,-7.0000,0.0000,6.2160,5.0400,-7.0000,0.0000,6.2160,5.0400,-7.0000,0.0000,0.0000},
				{0.0200,29.3140,-11.2000,6.3140,4.9000,-7.0000,-0.0000,6.3140,4.9000,-7.0000,0.0000,6.3140,4.9000,-7.0000,-0.0000,0.0000},
				{0.0200,29.4092,-11.2000,6.4092,4.7600,-7.0000,0.0000,6.4092,4.7600,-7.0000,0.0000,6.4092,4.7600,-7.0000,0.0000,0.0000},
				{0.0200,29.5016,-11.2000,6.5016,4.6200,-7.0000,0.0000,6.5016,4.6200,-7.0000,0.0000,6.5016,4.6200,-7.0000,0.0000,0.0000},
				{0.0200,29.5912,-11.2000,6.5912,4.4800,-7.0000,0.0000,6.5912,4.4800,-7.0000,0.0000,6.5912,4.4800,-7.0000,0.0000,0.0000},
				{0.0200,29.6780,-11.2000,6.6780,4.3400,-7.0000,-0.0000,6.6780,4.3400,-7.0000,0.0000,6.6780,4.3400,-7.0000,-0.0000,0.0000},
				{0.0200,29.7620,-11.2000,6.7620,4.2000,-7.0000,0.0000,6.7620,4.2000,-7.0000,0.0000,6.7620,4.2000,-7.0000,0.0000,0.0000},
				{0.0200,29.8432,-11.2000,6.8432,4.0600,-7.0000,-0.0000,6.8432,4.0600,-7.0000,0.0000,6.8432,4.0600,-7.0000,-0.0000,0.0000},
				{0.0200,29.9216,-11.2000,6.9216,3.9200,-7.0000,0.0000,6.9216,3.9200,-7.0000,0.0000,6.9216,3.9200,-7.0000,0.0000,0.0000},
				{0.0200,29.9972,-11.2000,6.9972,3.7800,-7.0000,0.0000,6.9972,3.7800,-7.0000,0.0000,6.9972,3.7800,-7.0000,0.0000,0.0000},
				{0.0200,30.0700,-11.2000,7.0700,3.6400,-7.0000,-0.0000,7.0700,3.6400,-7.0000,0.0000,7.0700,3.6400,-7.0000,-0.0000,0.0000},
				{0.0200,30.1400,-11.2000,7.1400,3.5000,-7.0000,0.0000,7.1400,3.5000,-7.0000,0.0000,7.1400,3.5000,-7.0000,0.0000,0.0000},
				{0.0200,30.2072,-11.2000,7.2072,3.3600,-7.0000,-0.0000,7.2072,3.3600,-7.0000,0.0000,7.2072,3.3600,-7.0000,-0.0000,0.0000},
				{0.0200,30.2716,-11.2000,7.2716,3.2200,-7.0000,0.0000,7.2716,3.2200,-7.0000,0.0000,7.2716,3.2200,-7.0000,0.0000,0.0000},
				{0.0200,30.3332,-11.2000,7.3332,3.0800,-7.0000,-0.0000,7.3332,3.0800,-7.0000,0.0000,7.3332,3.0800,-7.0000,-0.0000,0.0000},
				{0.0200,30.3920,-11.2000,7.3920,2.9400,-7.0000,0.0000,7.3920,2.9400,-7.0000,0.0000,7.3920,2.9400,-7.0000,0.0000,0.0000},
				{0.0200,30.4480,-11.2000,7.4480,2.8000,-7.0000,0.0000,7.4480,2.8000,-7.0000,0.0000,7.4480,2.8000,-7.0000,0.0000,0.0000},
				{0.0200,30.5012,-11.2000,7.5012,2.6600,-7.0000,-0.0000,7.5012,2.6600,-7.0000,0.0000,7.5012,2.6600,-7.0000,-0.0000,0.0000},
				{0.0200,30.5516,-11.2000,7.5516,2.5200,-7.0000,0.0000,7.5516,2.5200,-7.0000,0.0000,7.5516,2.5200,-7.0000,0.0000,0.0000},
				{0.0200,30.5992,-11.2000,7.5992,2.3800,-7.0000,-0.0000,7.5992,2.3800,-7.0000,0.0000,7.5992,2.3800,-7.0000,-0.0000,0.0000},
				{0.0200,30.6440,-11.2000,7.6440,2.2400,-7.0000,0.0000,7.6440,2.2400,-7.0000,0.0000,7.6440,2.2400,-7.0000,0.0000,0.0000},
				{0.0200,30.6860,-11.2000,7.6860,2.1000,-7.0000,0.0000,7.6860,2.1000,-7.0000,0.0000,7.6860,2.1000,-7.0000,0.0000,0.0000},
				{0.0200,30.7252,-11.2000,7.7252,1.9600,-7.0000,-0.0000,7.7252,1.9600,-7.0000,0.0000,7.7252,1.9600,-7.0000,-0.0000,0.0000},
				{0.0200,30.7616,-11.2000,7.7616,1.8200,-7.0000,0.0000,7.7616,1.8200,-7.0000,0.0000,7.7616,1.8200,-7.0000,0.0000,0.0000},
				{0.0200,30.7952,-11.2000,7.7952,1.6800,-7.0000,0.0000,7.7952,1.6800,-7.0000,0.0000,7.7952,1.6800,-7.0000,0.0000,0.0000},
				{0.0200,30.8260,-11.2000,7.8260,1.5400,-7.0000,-0.0000,7.8260,1.5400,-7.0000,0.0000,7.8260,1.5400,-7.0000,-0.0000,0.0000},
				{0.0200,30.8540,-11.2000,7.8540,1.4000,-7.0000,0.0000,7.8540,1.4000,-7.0000,0.0000,7.8540,1.4000,-7.0000,0.0000,0.0000},
				{0.0200,30.8792,-11.2000,7.8792,1.2600,-7.0000,-0.0000,7.8792,1.2600,-7.0000,0.0000,7.8792,1.2600,-7.0000,-0.0000,0.0000},
				{0.0200,30.9016,-11.2000,7.9016,1.1200,-7.0000,0.0000,7.9016,1.1200,-7.0000,0.0000,7.9016,1.1200,-7.0000,0.0000,0.0000},
				{0.0200,30.9212,-11.2000,7.9212,0.9800,-7.0000,-0.0000,7.9212,0.9800,-7.0000,0.0000,7.9212,0.9800,-7.0000,-0.0000,0.0000},
				{0.0200,30.9380,-11.2000,7.9380,0.8400,-7.0000,0.0000,7.9380,0.8400,-7.0000,0.0000,7.9380,0.8400,-7.0000,0.0000,0.0000},
				{0.0200,30.9520,-11.2000,7.9520,0.7000,-7.0000,0.0000,7.9520,0.7000,-7.0000,0.0000,7.9520,0.7000,-7.0000,0.0000,0.0000},
				{0.0200,30.9632,-11.2000,7.9632,0.5600,-7.0000,-0.0000,7.9632,0.5600,-7.0000,0.0000,7.9632,0.5600,-7.0000,-0.0000,0.0000},
				{0.0200,30.9716,-11.2000,7.9716,0.4200,-7.0000,0.0000,7.9716,0.4200,-7.0000,0.0000,7.9716,0.4200,-7.0000,0.0000,0.0000},
				{0.0200,30.9772,-11.2000,7.9772,0.2800,-7.0000,-0.0000,7.9772,0.2800,-7.0000,0.0000,7.9772,0.2800,-7.0000,-0.0000,0.0000},
				{0.0200,30.9800,-11.2000,7.9800,0.1400,-7.0000,0.0000,7.9800,0.1400,-7.0000,0.0000,7.9800,0.1400,-7.0000,0.0000,0.0000},
				{0.0200,30.9800,-11.2000,7.9800,0.0000,-7.0000,-0.0000,7.9800,0.0000,-7.0000,0.0000,7.9800,0.0000,-7.0000,-0.0000,0.0000},
				{0.0200,30.9772,-11.2000,7.9828,0.1400,7.0000,700.0000,7.9772,-0.1400,-7.0000,0.0000,7.9828,0.1400,7.0000,700.0000,0.0000},

	    };

	@Override
	public double[][] getPath() {
	    return points;
	}
}