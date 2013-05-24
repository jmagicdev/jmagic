package org.rnd.jmagic.abilityTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class AsThisEntersTheBattlefieldChooseAColor extends StaticAbility
{
	/**
	 * @eparam CAUSE: the reason for choosing a color
	 * @eparam PLAYER: the player choosing a color
	 * @eparam SOURCE: the object to store the choice on
	 * @eparam RESULT: the chosen color
	 */
	public static final EventType CHOOSE_A_COLOR = new EventType("CHOOSE_A_COLOR")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Linkable link = parameters.get(Parameter.SOURCE).getOne(Linkable.class).getPhysical();

			Color color = player.chooseColor(((StaticAbility)link).getSourceID());
			link.getLinkManager().addLinkInformation(color);
			event.setResult(Identity.instance(color));
			return color != null;
		}
	};

	public AsThisEntersTheBattlefieldChooseAColor(GameState state, String creatureName)
	{
		super(state, "As " + creatureName + " enters the battlefield, choose a color.");

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As " + creatureName + " enters the battlefield, choose a color.");
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator replacedMove = replacement.replacedByThis();

		EventFactory factory = new EventFactory(CHOOSE_A_COLOR, "Choose a color.");
		factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
		factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(replacedMove));
		factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
		replacement.addEffect(factory);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}
}
