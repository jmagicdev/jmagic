package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.trackers.*;

@Name("Keranos, God of Storms")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class KeranosGodofStorms extends Card
{
	public static final class KeranosGodofStormsAbility1 extends StaticAbility
	{
		public KeranosGodofStormsAbility1(GameState state)
		{
			super(state, "As long as your devotion to blue and red is less than seven, Keranos isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.BLUE, Color.RED));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class KeranosGodofStormsAbility2 extends StaticAbility
	{
		public KeranosGodofStormsAbility2(GameState state)
		{
			super(state, "Reveal the first card you draw on each of your turns.");

			SetGenerator youveDrawn = Intersect.instance(DrawnACardThisTurn.instance(), You.instance());
			SetGenerator yourFirstDraw = Not.instance(youveDrawn);
			SetGenerator yourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
			SetGenerator yourFirstDrawYourTurn = Both.instance(yourFirstDraw, yourTurn);
			this.canApply = Both.instance(this.canApply, yourFirstDrawYourTurn);
			state.ensureTracker(new DrawTracker());

			SimpleEventPattern drawOneCard = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawOneCard.put(EventType.Parameter.PLAYER, You.instance());
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "Reveal the first card you draw on each of your turns.", drawOneCard);

			SetGenerator originalEffect = EventParts.instance(replacement.replacedByThis());

			EventFactory drawAndReveal = new EventFactory(EventType.DRAW_AND_REVEAL, "Draw a card and reveal it");
			drawAndReveal.parameters.put(EventType.Parameter.EVENT, originalEffect);
			drawAndReveal.setLink(this);
			replacement.addEffect(drawAndReveal);

			this.addEffectPart(replacementEffectPart(replacement));

			this.getLinkManager().addLinkClass(LandEqualsDraw.class);
			this.getLinkManager().addLinkClass(NonlandEqualsBolt.class);
		}
	}

	public static final class LandEqualsDraw extends EventTriggeredAbility
	{
		public LandEqualsDraw(GameState state)
		{
			super(state, "Whenever you reveal a land card this way, draw a card.");
			this.getLinkManager().addLinkClass(KeranosGodofStormsAbility2.class);

			SimpleEventPattern drawALand = new SimpleEventPattern(EventType.DRAW_AND_REVEAL);
			drawALand.withResult(new ZoneChangeContaining(HasType.instance(Type.LAND)));
			this.addPattern(new LinkedEventPattern(drawALand, LinkedTo.instance(This.instance())));

			this.addEffect(drawACard());
		}
	}

	public static final class NonlandEqualsBolt extends EventTriggeredAbility
	{
		public NonlandEqualsBolt(GameState state)
		{
			super(state, "Whenever you reveal a nonland card this way, Keranos deals 3 damage to target creature or player.");
			this.getLinkManager().addLinkClass(KeranosGodofStormsAbility2.class);

			SetGenerator nonland = RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND));

			SimpleEventPattern drawANonland = new SimpleEventPattern(EventType.DRAW_AND_REVEAL);
			drawANonland.withResult(nonland);
			this.addPattern(new LinkedEventPattern(drawANonland, LinkedTo.instance(This.instance())));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Keranos deals 3 damage to target creature or player."));
		}
	}

	public KeranosGodofStorms(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to blue and red is less than seven, Keranos
		// isn't a creature.
		this.addAbility(new KeranosGodofStormsAbility1(state));

		// Reveal the first card you draw on each of your turns. Whenever you
		// reveal a land card this way, draw a card. Whenever you reveal a
		// nonland card this way, Keranos deals 3 damage to target creature or
		// player.
		this.addAbility(new KeranosGodofStormsAbility2(state));
		this.addAbility(new LandEqualsDraw(state));
		this.addAbility(new NonlandEqualsBolt(state));
	}
}
