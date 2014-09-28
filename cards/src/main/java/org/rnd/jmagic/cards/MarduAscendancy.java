package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Ascendancy")
@Types({Type.ENCHANTMENT})
@ManaCost("RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class MarduAscendancy extends Card
{
	public static final class MarduAscendancyAbility0 extends EventTriggeredAbility
	{
		public MarduAscendancyAbility0(GameState state)
		{
			super(state, "Whenever a nontoken creature you control attacks, put a 1/1 red Goblin creature token onto the battlefield tapped and attacking.");

			SetGenerator yourNontokens = RelativeComplement.instance(CREATURES_YOU_CONTROL, Tokens.instance());
			this.addPattern(whenXAttacks(yourNontokens));

			CreateTokensFactory goblin = new CreateTokensFactory(1, 1, 1, "Put a 1/1 red Goblin creature token onto the battlefield tapped and attacking.");
			goblin.setColors(Color.RED);
			goblin.setSubTypes(SubType.GOBLIN);
			goblin.setTappedAndAttacking(null);
			this.addEffect(goblin.getEventFactory());
		}
	}

	public static final class MarduAscendancyAbility1 extends ActivatedAbility
	{
		public MarduAscendancyAbility1(GameState state)
		{
			super(state, "Sacrifice Mardu Ascendancy: Creatures you control get +0/+3 until end of turn.");
			this.addCost(sacrificeThis("Mardu Ascendancy"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +0, +3, "Creatures you control gets +0/+3 until end of turn."));
		}
	}

	public MarduAscendancy(GameState state)
	{
		super(state);

		// Whenever a nontoken creature you control attacks, put a 1/1 red
		// Goblin creature token onto the battlefield tapped and attacking.
		this.addAbility(new MarduAscendancyAbility0(state));

		// Sacrifice Mardu Ascendancy: Creatures you control get +0/+3 until end
		// of turn.
		this.addAbility(new MarduAscendancyAbility1(state));
	}
}
