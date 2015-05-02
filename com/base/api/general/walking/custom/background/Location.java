package scripts.CombatAIO.com.base.api.general.walking.custom.background;

public enum Location {

	Lumbridge {
		@Override
		void teleport() {
			//if()

		}
	},
	Varrock {
		@Override
		void teleport() {
			// TODO Auto-generated method stub

		}
	},
	House {
		@Override
		void teleport() {
			// TODO Auto-generated method stub

		}
	},
	Ardogoune {
		@Override
		void teleport() {
			// TODO Auto-generated method stub

		}
	},
	Falador {
		@Override
		void teleport() {
			// TODO Auto-generated method stub

		}
	},
	Camelot {
		@Override
		void teleport() {
			// TODO Auto-generated method stub

		}
	};

	abstract void teleport();

}
