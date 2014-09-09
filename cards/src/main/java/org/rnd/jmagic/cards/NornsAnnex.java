package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Norn's Annex")
@Types({Type.ARTIFACT})
@ManaCost("3(W/P)(W/P)")
@ColorIdentity({Color.WHITE})
public final class NornsAnnex extends Card
{
	public static final class NornsAnnexAbility0 extends StaticAbility
	{
		public NornsAnnexAbility0(GameState state)
		{
			super(state, "Creatures can't attack you or a planeswalker you control unless their controller pays (w/p) for each of those creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("(w/p)")));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Union.instance(You.instance(), Intersect.instance(HasType.instance(Type.PLANESWALKER), ControlledBy.instance(You.instance()))));
			this.addEffectPart(part);
		}
	}

	public NornsAnnex(GameState state)
	{
		super(state);

		// Creatures can't attack you or a planeswalker you control unless their
		// controller pays (w/p) for each of those creatures.
		this.addAbility(new NornsAnnexAbility0(state));
	}
}
