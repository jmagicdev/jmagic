package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sacred Mesa")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SacredMesa extends Card
{
	public static final class SacredMesaAbility0 extends EventTriggeredAbility
	{
		public SacredMesaAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Sacred Mesa unless you sacrifice a Pegasus.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacThis = sacrificeThis("Sacred Mesa");
			EventFactory sacAPegasus = sacrifice(You.instance(), 1, HasSubType.instance(SubType.PEGASUS), "Sacrifice a Pegasus");
			this.addEffect(unless(You.instance(), sacThis, sacAPegasus, "Sacrifice Sacred Mesa unless you sacrifice a Pegasus."));
		}
	}

	public static final class SacredMesaAbility1 extends ActivatedAbility
	{
		public SacredMesaAbility1(GameState state)
		{
			super(state, "(1)(W): Put a 1/1 white Pegasus creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("(1)(W)"));

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Pegasus creature token with flying onto the battlefield.");
			f.setColors(Color.WHITE);
			f.setSubTypes(SubType.PEGASUS);
			f.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(f.getEventFactory());
		}
	}

	public SacredMesa(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, sacrifice Sacred Mesa unless you
		// sacrifice a Pegasus.
		this.addAbility(new SacredMesaAbility0(state));

		// (1)(W): Put a 1/1 white Pegasus creature token with flying onto the
		// battlefield.
		this.addAbility(new SacredMesaAbility1(state));
	}
}
