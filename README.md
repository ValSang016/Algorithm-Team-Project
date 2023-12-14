# Algorithm-Team-Project
## Activity-selection과 Knapsack 알고리즘을 변형하여 공간 할당 문제 해결
## 2학년 2학기 팀프로젝트로 공간 대여 사업에서 사업자가 사용할 수 있는 어플리케이션 개발

We developed a space allocation Android app using Android Studio. This app aims to provide automated space reservation confirmation services to space rental operators, enhancing their convenience.
This android studio project file contains various functionalities related to handling databases, utilizing stored data to create reservation lists, and generating corresponding tables.

The key algorithm for the space application is in Activity3.java file.
This algorithm employs the activity selection and knapsack algorithms for each meeting room, aiming to maximize the reserved time. It computes all feasible subsets and allocates reservations to rooms based on the subset with the maximum reservation time. This cycle continues for remaining reservations, resulting in the final reservation list.
