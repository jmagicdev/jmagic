package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Adaptive Automaton")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({})
public final class AdaptiveAutomaton extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("AdaptiveAutomation", "Choose a creature type.", true);

	public static final class AdaptiveAutomatonAbility0 extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public AdaptiveAutomatonAbility0(GameState state)
		{
			super(state, "As Adaptive Automation enters the battlefield, choose a creature type.");

			this.getLinkManager().addLinkClass(AdaptiveAutomatonAbility1.class);
			this.getLinkManager().addLinkClass(AdaptiveAutomatonAbility2.class);
		}
	}

	public static final class AdaptiveAutomatonAbility1 extends StaticAbility
	{
		public AdaptiveAutomatonAbility1(GameState state)
		{
			super(state, "Adaptive Automaton is the chosen type in addition to its other types.");
			this.addEffectPart(addType(This.instance(), ChosenFor.instance(LinkedTo.instance(Identity.instance(this)))));

			this.getLinkManager().addLinkClass(AdaptiveAutomatonAbility0.class);
		}
	}

	public static final class AdaptiveAutomatonAbility2 extends StaticAbility
	{
		public AdaptiveAutomatonAbility2(GameState state)
		{
			super(state, "Other creatures you control of the chosen type get +1/+1.");

			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator critters = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(chosenType));

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(critters, This.instance()), +1, +1));

			this.getLinkManager().addLinkClass(AdaptiveAutomatonAbility0.class);
		}
	}

	public AdaptiveAutomaton(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// As Adaptive Automaton enters the battlefield, choose a creature type.
		this.addAbility(new AdaptiveAutomatonAbility0(state));

		// Adaptive Automaton is the chosen type in addition to its other types.
		this.addAbility(new AdaptiveAutomatonAbility1(state));

		// Other creatures you control of the chosen type get +1/+1.
		this.addAbility(new AdaptiveAutomatonAbility2(state));
	}
}
