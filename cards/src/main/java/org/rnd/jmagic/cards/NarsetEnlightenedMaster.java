package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Narset, Enlightened Master")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("3URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class NarsetEnlightenedMaster extends Card
{
	public static final class NarsetEnlightenedMasterAbility1 extends EventTriggeredAbility
	{
		public NarsetEnlightenedMasterAbility1(GameState state)
		{
			super(state, "Whenever Narset, Enlightened Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.");
			this.addPattern(whenThisAttacks());

			SetGenerator top = TopCards.instance(4, LibraryOf.instance(You.instance()));
			EventFactory exile = exile(top, "Exile the top four cards of your library.");
			this.addEffect(exile);

			SetGenerator exiled = NewObjectOf.instance(EffectResult.instance(exile));
			SetGenerator noncreatures = RelativeComplement.instance(exiled, HasType.instance(Type.CREATURE));

			PlayPermission permission = new PlayPermission(You.instance());
			permission.forceAlternateCost(Empty.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, noncreatures);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
			this.addEffect(createFloatingEffect("Until end of turn, you may play that card without paying its mana cost.", part));
		}
	}

	public NarsetEnlightenedMaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// First strike, hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Whenever Narset, Enlightened Master attacks, exile the top four cards
		// of your library. Until end of turn, you may cast noncreature cards
		// exiled with Narset this turn without paying their mana costs.
		this.addAbility(new NarsetEnlightenedMasterAbility1(state));
	}
}
