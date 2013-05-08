package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

public abstract class AsThisEntersTheBattlefieldChooseACreatureType extends StaticAbility
{
	public AsThisEntersTheBattlefieldChooseACreatureType(GameState state, String abilityText)
	{
		super(state, abilityText);

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Choose a creature type");
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator originalEvent = replacement.replacedByThis();

		EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a creature type.");
		factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
		factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(SubType.getAllCreatureTypes()));
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_CREATURE_TYPE));
		factory.setLink(this);
		replacement.addEffect(factory);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}
}
