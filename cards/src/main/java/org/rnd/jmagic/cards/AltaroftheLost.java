package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Altar of the Lost")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AltaroftheLost extends Card
{
	public static final class Flashbacked implements SetPattern
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			for(GameObject o: set.getAll(GameObject.class))
			{
				if(!o.hasAbility(org.rnd.jmagic.abilities.keywords.Flashback.class))
					continue;
				if(!state.<Zone>get(o.zoneCastFrom).isGraveyard())
					continue;
				return true;
			}
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// nothing to do here
		}
	}

	public static final class AltaroftheLostAbility1 extends ActivatedAbility
	{
		public AltaroftheLostAbility1(GameState state)
		{
			super(state, "(T): Add two mana in any combination of colors to your mana pool. Spend this mana only to cast spells with flashback from a graveyard.");
			this.costsTap = true;

			EventFactory makeMana = new EventFactory(ADD_RESTRICTED_MANA, "Add two mana in any combination of colors to your mana pool. Spend this mana only to cast spells with flashback from a graveyard.");
			makeMana.parameters.put(EventType.Parameter.SOURCE, This.instance());
			makeMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			makeMana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new Flashbacked()));
			makeMana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
			makeMana.parameters.put(EventType.Parameter.MANA, Identity.instance(Color.allColors()));

			// i hate this :-/ but the "number" parameter to add_restricted_mana
			// causes the mana to both be the same color (vedalken engineer
			// wants it that way) so there's no other way to do this.
			// TODO : fix this, because however we write Contamination it will
			// have to replace one event
			EventFactory makeAnotherMana = new EventFactory(ADD_RESTRICTED_MANA, "");
			makeAnotherMana.parameters.putAll(makeMana.parameters);

			this.addEffect(makeMana);
			this.addEffect(makeAnotherMana);
		}
	}

	public AltaroftheLost(GameState state)
	{
		super(state);

		// Altar of the Lost enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add two mana in any combination of colors to your mana pool.
		// Spend this mana only to cast spells with flashback from a graveyard.
		this.addAbility(new AltaroftheLostAbility1(state));
	}
}
