package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Engineered Plague")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class EngineeredPlague extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("EngineeredPlague", "Choose a creature type.", true);

	public static final class EngineeredPlagueAbility0 extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public EngineeredPlagueAbility0(GameState state)
		{
			super(state, "As Engineered Plague enters the battlefield, choose a creature type.");

			this.getLinkManager().addLinkClass(EngineeredPlagueAbility1.class);
		}
	}

	public static final class EngineeredPlagueAbility1 extends StaticAbility
	{
		public EngineeredPlagueAbility1(GameState state)
		{
			super(state, "All creatures of the chosen type get -1/-1.");

			this.getLinkManager().addLinkClass(EngineeredPlagueAbility0.class);

			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator who = Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(chosenType));
			this.addEffectPart(modifyPowerAndToughness(who, -1, -1));
		}
	}

	public EngineeredPlague(GameState state)
	{
		super(state);

		// As Engineered Plague enters the battlefield, choose a creature type.
		this.addAbility(new EngineeredPlagueAbility0(state));

		// All creatures of the chosen type get -1/-1.
		this.addAbility(new EngineeredPlagueAbility1(state));
	}
}
