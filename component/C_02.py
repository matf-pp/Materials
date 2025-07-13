import sys, json

class Player:
  def __init__(self, number, name, surname, height, age):
    self.number = number
    self.name = name
    self.surname = surname
    self.height = height
    self.age = age
    self.__position = None

  def __str__(self):
    return self.name + ' ' + self.surname + ' no. ' + str(self.number) + ' positon: ' + str(self.get_position())
    
  def assign_position(self):
    if self.height < 190:
      self.__position = 1  
    elif self.height < 200:
      self.__position = 3  
    else:
      self.__position = 5
      
  def get_position(self):
    return self.__position
    
def load_players(json_path):
  with open(json_path, 'r') as f:
    return [Player(player['dres'], player['ime'], player['prezime'], player['visina'], player['godine']) for player in json.load(f)]

def age_filter(players, age_limit):
  return list(filter(lambda p: p.age<age_limit, players))
  
def assign_positions(players):
  [player.assign_position() for player in players]
  
def get_highest(players, position):
	data = [x.height for x in filter(lambda x: x.get_position()==position, players)]
	return -1 if len(data)==0 else max(data)
  
if __name__ == "__main__":
 	assert len(sys.argv)==2, "Invalid call."
 	db = load_players(sys.argv[1])
 	print('Initial: ')
 	for player in db:
 		print(player)
 		
 	age_limit = float(input("Enter age limit: "))
 	db = age_filter(db, age_limit)
 	print('Filered: ')
 	for player in db:
 		print(player)
 		
 	assign_positions(db)
 	print('Postion assigned: ')
 	for player in db:
 		print(player)
 		
 	position = int(input("Enter position: "))
 	print('Result: ')
 	print(get_highest(db, position))
 		
 		
 
