package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scion of Oona")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.SOLDIER})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ScionofOona extends Card
{
	public static final class GrantShroud extends StaticAbility
	{
		public GrantShroud(GameState state)
		{
			super(state, "Other Faeries you control have shroud.");

			SetGenerator faeries = Intersect.instance(HasSubType.instance(SubType.FAERIE), CREATURES_YOU_CONTROL);
			SetGenerator who = RelativeComplement.instance(faeries, This.instance());
			this.addEffectPart(addAbilityToObject(who, org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public ScionofOona(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Faerie creatures you control get +1/+1.
		SetGenerator faeries = Intersect.instance(HasSubType.instance(SubType.FAERIE), CREATURES_YOU_CONTROL);
		SetGenerator who = RelativeComplement.instance(faeries, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, who, "Other Faerie creatures you control", +1, +1, true));

		// Other Faeries you control have shroud.
		this.addAbility(new GrantShroud(state));
	}
}
