package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sarkhan Vol")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SARKHAN})
@ManaCost("2RG")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class SarkhanVol extends Card
{
	public static final class SarkhanVolAbility0 extends LoyaltyAbility
	{
		public SarkhanVolAbility0(GameState state)
		{
			super(state, +1, "Creatures you control get +1/+1 and gain haste until end of turn.");
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 and gain haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public static final class SarkhanVolAbility1 extends LoyaltyAbility
	{
		public SarkhanVolAbility1(GameState state)
		{
			super(state, -2, "Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.");

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", controlPart));

			this.addEffect(untap(targetedBy(target), "Untap that creature."));

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
		}
	}

	public static final class SarkhanVolAbility2 extends LoyaltyAbility
	{
		public SarkhanVolAbility2(GameState state)
		{
			super(state, -6, "Put five 4/4 red Dragon creature tokens with flying onto the battlefield.");

			CreateTokensFactory tokens = new CreateTokensFactory(5, 4, 4, "Put five 4/4 red Dragon creature tokens with flying onto the battlefield.");
			tokens.setColors(Color.RED);
			tokens.setSubTypes(SubType.DRAGON);
			tokens.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public SarkhanVol(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Creatures you control get +1/+1 and gain haste until end of turn.
		this.addAbility(new SarkhanVolAbility0(state));

		// -2: Gain control of target creature until end of turn. Untap that
		// creature. It gains haste until end of turn.
		this.addAbility(new SarkhanVolAbility1(state));

		// -6: Put five 4/4 red Dragon creature tokens with flying onto the
		// battlefield.
		this.addAbility(new SarkhanVolAbility2(state));
	}
}
