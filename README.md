### Problem
A growing e-commerce company is faced with the challenge of efficient routing of packages to reduce the delivery time for the
customers. They have multiple warehouses across the country but the problem is efficient routing of packages within these
warehouses. The last mile delivery is not the challenge. For this, the company is looking for vendors who can help them to find out
optimal routing path for their packages from/to their warehouses in different cities.

You have been asked to implement a system that will route packages across their delivery network including shippings and returns
from/to their warehouses.

You can assume that logistic and infrastructure like transport, manpower is not a challenge. And there will be a single warehouse per city.

### Input
1. Warehouse information - List of company warehouses across the country
Format: **<wh_id> <city>**
2. Delivery network - Connection between warehouses and associated travel time
Format: **<WH 1> <WH 2> <travel Time>**
3. Packages - Package which have to be routed, includes deliveries, returns, etc.
Format: **<package_id> <wh_id> <city>**
All packages start their journey from a warehouse and are destined to a warehouse (of mentioned city) in the country.

*All packages marked with a city **“RETURN”** are routed to **RETURN_WH** warehouse, which happens to be the warehouse that handles
all returned items.

### Output
The output is the optimal routing to get packages to their destinations.
Format: **<package_id> <wh_1> <wh_2> [<wh_3>, …] : <total_travel_time>**
The output should be in the same order as the package list section (Section 3) of the input.

### Sample
| Input                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Output                                                                                                                                              |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| # Section 1: List of company warehouses in the country<br>WH1 Pune<br>WH2 Mumbai<br>WH3 Delhi<br>WH4 Lucknow<br>WH5 Kanpur<br>WH6 Chennai<br>WH7 Hyderabad<br>WH8 Ranchi<br>WH9 Ahmedabad<br>WH10 Jaipur<br>WH11 Raipur<br>WH12 Bangalore<br><br><br># Section 2: Delivery network<br>WH11 WH5 5<br>WH5 RETURN_WH 5<br>WH5 WH10 4<br>WH5 WH1 6<br>WH1 WH2 1<br>WH2 WH3 1<br>WH3 WH4 1<br>WH10 WH9 1<br>WH9 WH8 1<br>WH8 WH7 1<br>WH7 WH6 1<br><br><br># Section 3: Packages<br>pkg1 WH11 Pune<br>pkg2 WH5 Lucknow<br>pkg3 WH2 Pune<br>pkg4 WH8 Kanpur<br>pkg5 WH7 RETURN | pkg1 WH11 WH5 WH1 : 11<br>pkg2 WH5 WH1 WH2 WH3 WH4 : 9<br>pkg3 WH2 WH1 : 1<br>pkg4 WH8 WH9 WH10 WH5 : 6<br>pkg5 WH7 WH8 WH9 WH10 WH5 RETURN_WH : 12 |

### Solution
Solution uses [This Dijkstra's shortest path algorithm implementation](https://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html)