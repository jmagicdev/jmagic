package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Wizardry")
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CurseofWizardry extends Card
{
	public static final class ChooseAColor extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ChooseAColor(GameState state)
		{
			super(state, "Curse of Wizardry");

			this.getLinkManager().addLinkClass(ColorPain.class);
		}
	}

	public static final class ColorPain extends EventTriggeredAbility
	{
		public ColorPain(GameState state)
		{
			super(state, "Whenever a player casts a spell of the chosen color, that player loses 1 life.");

			this.getLinkManager().addLinkClass(ChooseAColor.class);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Intersect.instance(Spells.instance(), HasColor.instance(ChosenFor.instance(LinkedTo.instance(This.instance())))));
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			this.addEffect(loseLife(thatPlayer, 1, "That player loses 1 life."));
		}
	}

	public CurseofWizardry(GameState state)
	{
		super(state);

		// As Curse of Wizardry enters the battlefield, choose a color.
		this.addAbility(new ChooseAColor(state));

		// Whenever a player casts a spell of the chosen color, that player
		// loses 1 life.
		this.addAbility(new ColorPain(state));
	}
}
