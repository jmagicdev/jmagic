package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burrenton Forge-Tender")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.KITHKIN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class BurrentonForgeTender extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("BurrentonForgeTender", "Choose a red source of damage.", true);

	public static final class BurrentonForgeTenderAbility1 extends ActivatedAbility
	{
		public BurrentonForgeTenderAbility1(GameState state)
		{
			super(state, "Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of your choice would deal this turn.");
			this.addCost(sacrificeThis("Burrenton Forge-Tender"));

			EventFactory chooseSource = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a red source of damage");
			chooseSource.parameters.put(EventType.Parameter.PLAYER, You.instance());
			chooseSource.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasColor.instance(Color.RED), AllSourcesOfDamage.instance()));
			chooseSource.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.DAMAGE_SOURCE, REASON));
			this.addEffect(chooseSource);

			SetGenerator chosenSource = EffectResult.instance(chooseSource);

			this.addEffect(createFloatingReplacement(new PreventAllFrom(state.game, chosenSource, "a red source of your choice"), "Prevent all damage a red source of your choice would deal this turn."));
		}
	}

	public BurrentonForgeTender(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Protection from red
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromRed(state));

		// Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of
		// your choice would deal this turn.
		this.addAbility(new BurrentonForgeTenderAbility1(state));
	}
}
