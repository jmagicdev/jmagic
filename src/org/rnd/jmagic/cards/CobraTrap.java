package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cobra Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CobraTrap extends Card
{
	public static final class CobraTrapGenerator extends SetGenerator
	{
		public static CobraTrapGenerator instance(SetGenerator you)
		{
			return new CobraTrapGenerator(you);
		}

		private SetGenerator you;

		private CobraTrapGenerator(SetGenerator you)
		{
			this.you = you;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = this.you.evaluate(state, thisObject).getOne(Player.class);
			java.util.Set<Player> opponents = OpponentsOf.instance(this.you).evaluate(state, thisObject).getAll(Player.class);
			Set ret = new Set();

			java.util.Map<Integer, Integer> ids = state.getTracker(DestroyedThisTurn.DestroyedTracker.class).getValue(state);

			for(java.util.Map.Entry<Integer, Integer> entry: ids.entrySet())
			{
				// Ignore anything the game is the cause of
				if(entry.getValue() == 0)
					continue;

				GameObject o = state.get(entry.getKey());
				if(o.getController(state).equals(you) && opponents.contains(state.<GameObject>get(entry.getValue()).getController(state)))
					ret.add(o);
			}

			return ret;
		}
	}

	public CobraTrap(GameState state)
	{
		super(state);

		// If a noncreature permanent under your control was destroyed this turn
		// by a spell or ability an opponent controlled, you may pay (G) rather
		// than pay Cobra Trap's mana cost.
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, "Cobra Trap", CobraTrapGenerator.instance(You.instance()), "If a noncreature permanent under your control was destroyed this turn by a spell or ability an opponent controlled", "(G)"));
		state.ensureTracker(new DestroyedThisTurn.DestroyedTracker());

		// Put four 1/1 green Snake creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(4, 1, 1, "Put four 1/1 green Snake creature tokens onto the battlefield.");
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.SNAKE);
		this.addEffect(tokens.getEventFactory());
	}
}
