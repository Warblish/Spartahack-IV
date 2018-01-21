import json
with open('proportions.json') as f:
	proportions = json.load(f)
with open('GlobalImpact.json') as f:	
	waterdata = json.load(f)['Global']
with open('stress.json') as f:
	stress = json.load(f)
def total_water(item):
	try:
		return item['Blue'] + item['Green'] + item['Grey']
	except TypeError:
		return 0
def get_impact(i):
	multiplier = 0.0
	for country in proportions[i]['countries']:
		#print(country)
		multiplier += float(stress.get(country, 0))
	return total_water(waterdata.get(str(i))) * multiplier
finaldata = {}
for itemid in proportions.keys():
	if get_impact(itemid):
		wdata = waterdata.get(str(itemid))
		finaldata[itemid] = {"impact": get_impact(itemid), "blue": wdata['Blue'],
		"green": wdata['Green'], "grey": wdata['Grey'], "name": wdata['Name']} 
print("Data Parsed")
with open('final.json', 'w') as f:
	json.dump(finaldata, f)

