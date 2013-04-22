package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fresh Meat")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class FreshMeat extends Card
{
	public static final class CreaturesPutIntoYourGraveyardThisTurn extends SetGenerator
	{
		private static final CreaturesPutIntoYourGraveyardThisTurn _instance = new CreaturesPutIntoYourGraveyardThisTurn();

		public static CreaturesPutIntoYourGraveyardThisTurn instance()
		{
			return _instance;
		}

		private CreaturesPutIntoYourGraveyardThisTurn()
		{
			// Singleton constructor
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			int player = You.instance().evaluate(state, thisObject).getOne(Player.class).ID;
			java.util.Map<Integer, java.util.Set<Integer>> values = state.getTracker(PutIntoGraveyardsFromBattlefieldThisTurn.DeathTracker.class).getValue(state);
			if(!values.containsKey(player))
				return Empty.set;
			Set ret = new Set();
			for(Integer id: values.get(player))
			{
				Identified object = state.get(id);
				if(object instanceof GameObject && ((GameObject)object).getTypes().contains(Type.CREATURE))
					ret.add(object);
			}
			return ret;
		}
	}

	public FreshMeat(GameState state)
	{
		super(state);

		// Put a 3/3 green Beast creature token onto the battlefield for each
		// creature put into your graveyard from the battlefield this turn.
		state.ensureTracker(new PutIntoGraveyardsFromBattlefieldThisTurn.DeathTracker());

		SetGenerator num = Count.instance(CreaturesPutIntoYourGraveyardThisTurn.instance());

		CreateTokensFactory factory = new CreateTokensFactory(num, "Put a 3/3 green Beast creature token onto the battlefield for each creature put into your graveyard from the battlefield this turn.");
		factory.addCreature(3, 3);
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.BEAST);
		this.addEffect(factory.getEventFactory());
	}
}
