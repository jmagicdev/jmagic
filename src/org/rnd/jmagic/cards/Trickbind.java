package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Trickbind")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Trickbind extends Card
{
	public Trickbind(GameState state)
	{
		super(state);

		// Split second
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// Counter target activated or triggered ability.
		SetGenerator target = targetedBy(this.addTarget(AbilitiesOnTheStack.instance(), "Counter target activated or triggered ability."));
		EventFactory counter = counter(target, "Counter target activated or triggered ability.");
		this.addEffect(counter);

		// If a permanent's ability is countered this way, activated abilities
		// of that permanent can't be activated this turn.
		SetGenerator source = AbilitySource.instance(EffectResult.instance(counter));
		SetGenerator sourceIsPermanent = Intersect.instance(source, Permanents.instance());

		SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
		prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(source));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
		EventFactory shutoff = createFloatingEffect("Activated abilities of that permanent can't be activated this turn", part);

		EventFactory shutoffIf = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If a permanent's ability is countered this way, activated abilities of that permanent can't be activated this turn.");
		shutoffIf.parameters.put(EventType.Parameter.IF, sourceIsPermanent);
		shutoffIf.parameters.put(EventType.Parameter.THEN, Identity.instance(shutoff));
		this.addEffect(shutoffIf);
	}
}
