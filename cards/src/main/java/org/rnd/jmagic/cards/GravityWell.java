package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gravity Well")
@Types({Type.ENCHANTMENT})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class GravityWell extends Card
{
	public static final class DropItLikeItsHot extends EventTriggeredAbility
	{
		public DropItLikeItsHot(GameState state)
		{
			super(state, "Whenever a creature with flying attacks, it loses flying until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)));
			this.addPattern(pattern);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffect(createFloatingEffect("It loses flying until end of turn.", part));
		}
	}

	public GravityWell(GameState state)
	{
		super(state);

		// Whenever a creature with flying attacks, it loses flying until end of
		// turn.
		this.addAbility(new DropItLikeItsHot(state));
	}
}
