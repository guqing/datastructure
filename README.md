<h1 align="center">Java Data Structrue</h1>
<div align="center">
Java数据结构与算法笔记以及示例代码 <a href="https://github.com/guqing/datastructure" target="_blank">datastructure</a>
</div>

## 简介

本文档为数据结构和算法学习笔记，主要作者 [@guqing](https://github.com/guqing) 一个Java界的小学生，并不是专业算法选手，希望有专业的小伙伴一起来改进。希望这个笔记能给你在学习算法的过程提供思路和源码方面的参考，全文大致按照数据结构/算法基本介绍，代码实现及测试，几道面试题三个部分来写。

### 订阅更新

本项目托管在https://github.com/guqing/datastructure你可以在 `GitHub `中 star 该项目查看更新。

## 许可证

本作品采用 **MIT许可证** 进行许可。**传播此文档时请注意遵循以上许可协议。** 关于本许可证的更多详情可参考 https://www.mit-license.org/

本着参与比主导更重要的开源精神，我将自己的数据结构与算法学习笔记公开和小伙伴们讨论，希望高手们不吝赐教。

## 如何贡献

如果你发现任何有错误的地方或是想更新/翻译本文档，请给我提[issues](https://github.com/guqing/datastructure/issues)或[pr](https://github.com/guqing/datastructure/pulls)

## 笔记目录

查看笔记戳这里☞[学习笔记](./docs/algorithms.md)

[github page](https://guqing.github.io/datastructure/docs/algorithms.md.html)

### 稀疏数组

### 队列

- 用数组实现一个顺序队列
- 数组实现一个循环队列

### 链表

- 单链表
  - `void add(T t)`
   - `void addAll(DoubleLinkedList list)`
   - `void remove(T t)`
   - `void remove(int index)`
   - `T get(int index)`
   - `int size()`
   - `String toString()`
- 双向链表(方法列表同上)
- 单向循环链表
- 实现单链表反转(两种实现方式)
- 单向循环链表解决瑟夫环问题

### 栈

- 用数组实现一个顺序栈
- 用链表实现一个链式栈

## 书籍推荐

- [《算法 第四版》](https://book.douban.com/subject/10432347/)（推荐，豆瓣评分 9.3，0.4K+人评价）：Java 语言描述，算法领域经典的参考书，全面介绍了关于算法和数据结构的必备知识，并特别针对排序、搜索、图处理和字符串处理进行了论述。书的内容非常多，可以说是 Java 程序员的必备书籍之一了。

- [《算法图解》](https://book.douban.com/subject/26979890/)（推荐，豆瓣评分 8.4，0.6K+人评价）：入门类型的书籍，读起来比较浅显易懂，适合没有算法基础或者说算法没学好的小伙伴用来入门。示例丰富，图文并茂，以让人容易理解的方式阐释了算法.读起来比较快，内容不枯燥！
- 《C算法》（推荐，豆瓣评分 8.0，48人评价：入门类型的书籍,阐述内容清楚、详尽、易懂与[《算法第四版》](https://book.douban.com/subject/10432347/)是同一作者，介绍了当今最重要的算法，共分2卷，[《C算法(第一卷):基础、数据结构、排序和摸索》](https://book.douban.com/subject/1169844/)，[《算法(第二卷：图算法)》](https://book.douban.com/subject/1152528/)。书中提供了用C语言描述的完整算法源程序，并且配有丰富的插图和练习。