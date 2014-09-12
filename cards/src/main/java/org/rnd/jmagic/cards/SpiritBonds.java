package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Spirit Bonds")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SpiritBonds extends Card
{
	public static final class SpiritBondsAbility0 extends EventTriggeredAbility
	{
		public SpiritBondsAbility0(GameState state)
		{
			super(state, "Whenever a nontoken creature enters the battlefield under your control, you may pay (W). If you do, put a 1/1 white Spirit creature token with flying onto the battlefield.");

			SetGenerator nontokens = RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance());
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(null, Battlefield.instance(), nontokens, You.instance(), false);
			this.addPattern(pattern);

			CreateTokensFactory spirit = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield.");
			spirit.setColors(Color.WHITE);
			spirit.setSubTypes(SubType.SPIRIT);
			this.addEffect(ifThen(youMayPay("(W)"), spirit.getEventFactory(), "You may pay (W). If you do, put a 1/1 white Spirit creature token with flying onto the battlefield."));
		}
	}

	public static final class SpiritBondsAbility1 extends ActivatedAbility
	{
		public SpiritBondsAbility1(GameState state)
		{
			super(state, "(1)(W), Sacrifice a Spirit: Target non-Spirit creature gains indestructible until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.SPIRIT), "sacrifice a Spirit"));

			SetGenerator nonSpirits = RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SPIRIT));
			SetGenerator target = targetedBy(this.addTarget(nonSpirits, "target non-Spirit creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Indestructible.class, "Target non-Spirit creature gains indestructible until end of turn."));
		}
	}

	public SpiritBonds(GameState state)
	{
		super(state);

		// Whenever a nontoken creature enters the battlefield under your
		// control, you may pay (W). If you do, put a 1/1 white Spirit creature
		// token with flying onto the battlefield.
		this.addAbility(new SpiritBondsAbility0(state));

		// (1)(W), Sacrifice a Spirit: Target non-Spirit creature gains
		// indestructible until end of turn. (Damage and effects that say
		// "destroy" don't destroy it.)
		this.addAbility(new SpiritBondsAbility1(state));
	}
}
