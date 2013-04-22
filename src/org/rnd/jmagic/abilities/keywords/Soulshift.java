package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Soulshift extends Keyword
{
	private final int N;

	public Soulshift(GameState state, int N)
	{
		super(state, "Soulshift " + N);
		this.N = N;
	}

	@Override
	public Soulshift create(Game game)
	{
		return new Soulshift(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new SoulshiftTrigger(this.state, this));

		return ret;
	}

	public static final class SoulshiftTrigger extends EventTriggeredAbility
	{
		private final int N;

		public SoulshiftTrigger(GameState state, Soulshift parent)
		{
			this(state, parent.N);
		}

		public SoulshiftTrigger(GameState state, int N)
		{
			super(state, "When this permanent is put into a graveyard from play, you may return target Spirit card with converted mana cost " + N + " or less from your graveyard to your hand.");
			this.N = N;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);
			SetGenerator controllersGraveyard = GraveyardOf.instance(controller);
			SetGenerator spirits = HasSubType.instance(SubType.SPIRIT);
			SetGenerator cmcLessThanN = HasConvertedManaCost.instance(Identity.instance(new org.rnd.util.NumberRange(0, N)));

			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			Target target = this.addTarget(Intersect.instance(cmcLessThanN, Intersect.instance(spirits, InZone.instance(controllersGraveyard))), "target Spirit card with converted mana cost " + N + " or less from your graveyard");

			EventFactory moveFactory = new EventFactory(EventType.MOVE_OBJECTS, ("Return target Spirit card with converted mana cost " + N + " or less from your graveyard to your hand"));
			moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveFactory.parameters.put(EventType.Parameter.TO, HandOf.instance(controller));
			moveFactory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(moveFactory, ("You may return target Spirit card with converted mana cost " + N + " or less from your graveyard to your hand.")));
		}

		@Override
		public SoulshiftTrigger create(Game game)
		{
			return new SoulshiftTrigger(game.physicalState, this.N);
		}
	}
}
