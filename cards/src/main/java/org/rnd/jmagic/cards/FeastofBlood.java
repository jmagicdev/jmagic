package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Feast of Blood")
@Types({Type.SORCERY})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class FeastofBlood extends Card
{
	public static final class PlayRestriction extends StaticAbility
	{
		public PlayRestriction(GameState state)
		{
			super(state, "Cast Feast of Blood only if you control two or more Vampires.");

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, You.instance());
			castSpell.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part prohibitEffect = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibitEffect.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(prohibitEffect);

			SetGenerator yourVampires = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()));
			SetGenerator restriction = Intersect.instance(Between.instance(null, 1), Count.instance(yourVampires));
			this.canApply = restriction;
		}
	}

	public FeastofBlood(GameState state)
	{
		super(state);

		// Cast Feast of Blood only if you control two or more Vampires.
		this.addAbility(new PlayRestriction(state));

		// Destroy target creature. You gain 4 life.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target creature."));
		this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
	}
}
