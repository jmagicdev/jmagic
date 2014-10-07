package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hour of Need")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class HourofNeed extends Card
{
	public HourofNeed(GameState state)
	{
		super(state);

		// Strive \u2014 Hour of Need costs (1)(U) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(1)(U)"));

		// Exile any number of target creatures.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));
		EventFactory exile = exile(target, "Exile any number of target creatures.");
		this.addEffect(exile);

		// For each creature exiled this way, its controller puts a 4/4 blue
		// Sphinx creature token with flying onto the battlefield.

		// we need to get the creature's controller, so this generator is
		// purposefully getting the old object. we make sure it was exiled using
		// FutureSelf.
		SetGenerator exiled = OldObjectOf.instance(EffectResult.instance(exile));

		DynamicEvaluation eachExiledCreature = DynamicEvaluation.instance();

		CreateTokensFactory sphinx = new CreateTokensFactory(1, 4, 4, "That creature's controller puts a 4/4 blue Sphinx creature token with flying onto the battlefield.");
		sphinx.setController(ControllerOf.instance(eachExiledCreature));
		sphinx.setColors(Color.BLUE);
		sphinx.setSubTypes(SubType.SPHINX);
		sphinx.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);

		SetGenerator wasExiled = Intersect.instance(InZone.instance(ExileZone.instance()), FutureSelf.instance(exiled));
		EventFactory eachToken = ifThen(wasExiled, sphinx.getEventFactory(), "That creature's controller puts a 4/4 blue Sphinx creature token with flying onto the battlefield.");

		EventFactory allTokens = new EventFactory(FOR_EACH, "For each creature exiled this way, its controller puts a 4/4 blue Sphinx creature token with flying onto the battlefield.");
		allTokens.parameters.put(EventType.Parameter.OBJECT, exiled);
		allTokens.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachExiledCreature));
		allTokens.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachToken));
		this.addEffect(allTokens);
	}
}
