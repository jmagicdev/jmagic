package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abzan Ascendancy")
@Types({Type.ENCHANTMENT})
@ManaCost("WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class AbzanAscendancy extends Card
{
	public static final class AbzanAscendancyAbility0 extends EventTriggeredAbility
	{
		public AbzanAscendancyAbility0(GameState state)
		{
			super(state, "When Abzan Ascendancy enters the battlefield, put a +1/+1 counter on each creature you control.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control."));
		}
	}

	public static final class AbzanAscendancyAbility1 extends EventTriggeredAbility
	{
		public AbzanAscendancyAbility1(GameState state)
		{
			super(state, "Whenever a nontoken creature you control dies, put a 1/1 white Spirit creature token with flying onto the battlefield.");

			SetGenerator nontokens = RelativeComplement.instance(HasType.instance(Type.CREATURE), Tokens.instance());
			SetGenerator yourNontokens = Intersect.instance(ControlledBy.instance(You.instance()), nontokens);
			this.addPattern(whenXDies(yourNontokens));

			CreateTokensFactory spirit = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield.");
			spirit.setColors(Color.WHITE);
			spirit.setSubTypes(SubType.SPIRIT);
			spirit.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(spirit.getEventFactory());
		}
	}

	public AbzanAscendancy(GameState state)
	{
		super(state);

		// When Abzan Ascendancy enters the battlefield, put a +1/+1 counter on
		// each creature you control.
		this.addAbility(new AbzanAscendancyAbility0(state));

		// Whenever a nontoken creature you control dies, put a 1/1 white Spirit
		// creature token with flying onto the battlefield.
		this.addAbility(new AbzanAscendancyAbility1(state));
	}
}
