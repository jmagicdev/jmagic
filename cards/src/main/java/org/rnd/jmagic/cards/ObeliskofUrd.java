package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Obelisk of Urd")
@Types({Type.ARTIFACT})
@ManaCost("6")
@ColorIdentity({})
public final class ObeliskofUrd extends Card
{
	public static final class ObeliskofUrdAbility1 extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public ObeliskofUrdAbility1(GameState state)
		{
			super(state, "As Obelisk of Urd enters the battlefield, choose a creature type.");
			this.getLinkManager().addLinkClass(ObeliskofUrdAbility2.class);
		}
	}

	public static final class ObeliskofUrdAbility2 extends StaticAbility
	{
		public ObeliskofUrdAbility2(GameState state)
		{
			super(state, "Creatures you control of the chosen type get +2/+2.");

			this.getLinkManager().addLinkClass(ObeliskofUrdAbility1.class);
			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator critters = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(chosenType));

			this.addEffectPart(modifyPowerAndToughness(critters, +2, +2));
		}
	}

	public ObeliskofUrd(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// As Obelisk of Urd enters the battlefield, choose a creature type.
		this.addAbility(new ObeliskofUrdAbility1(state));

		// Creatures you control of the chosen type get +2/+2.
		this.addAbility(new ObeliskofUrdAbility2(state));
	}
}
