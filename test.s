output:

	.globl	_global
	.p2align	2
	.type	_global,@function
_global:
ASMBlock: _global_block:
	mv	VirtualReg_0,s0
	mv	VirtualReg_1,s1
	mv	VirtualReg_2,s2
	mv	VirtualReg_3,s3
	mv	VirtualReg_4,s4
	mv	VirtualReg_5,s5
	mv	VirtualReg_6,s6
	mv	VirtualReg_7,s7
	mv	VirtualReg_8,s8
	mv	VirtualReg_9,s9
	mv	VirtualReg_10,s10
	mv	VirtualReg_11,s11
	j	_global_allocate
ASMBlock: _global_allocate:
	j	_global_entry
ASMBlock: _global_entry:
	j	_global_return
ASMBlock: _global_return:
	mv	s0,VirtualReg_0
	mv	s1,VirtualReg_1
	mv	s2,VirtualReg_2
	mv	s3,VirtualReg_3
	mv	s4,VirtualReg_4
	mv	s5,VirtualReg_5
	mv	s6,VirtualReg_6
	mv	s7,VirtualReg_7
	mv	s8,VirtualReg_8
	mv	s9,VirtualReg_9
	mv	s10,VirtualReg_10
	mv	s11,VirtualReg_11

	.globl	main
	.p2align	2
	.type	main,@function
main:
ASMBlock: main_block:
	mv	VirtualReg_0,s0
	mv	VirtualReg_1,s1
	mv	VirtualReg_2,s2
	mv	VirtualReg_3,s3
	mv	VirtualReg_4,s4
	mv	VirtualReg_5,s5
	mv	VirtualReg_6,s6
	mv	VirtualReg_7,s7
	mv	VirtualReg_8,s8
	mv	VirtualReg_9,s9
	mv	VirtualReg_10,s10
	mv	VirtualReg_11,s11
	j	main_allocate
ASMBlock: main_allocate:
	j	main_entry
ASMBlock: main_entry:
	li	VirtualReg_13,0
	mv	VirtualReg_12,VirtualReg_13
	li	VirtualReg_14,0
	mv	VirtualReg_12,VirtualReg_14
	j	main_return
	j	main_return
ASMBlock: main_return:
	mv	VirtualReg_15,VirtualReg_12
	mv	a0,VirtualReg_15
	mv	s0,VirtualReg_0
	mv	s1,VirtualReg_1
	mv	s2,VirtualReg_2
	mv	s3,VirtualReg_3
	mv	s4,VirtualReg_4
	mv	s5,VirtualReg_5
	mv	s6,VirtualReg_6
	mv	s7,VirtualReg_7
	mv	s8,VirtualReg_8
	mv	s9,VirtualReg_9
	mv	s10,VirtualReg_10
	mv	s11,VirtualReg_11

