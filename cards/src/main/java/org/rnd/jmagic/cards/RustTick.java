package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rust Tick")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.INSECT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RustTick extends Card
{
	public static final class RustTickAbility0 extends StaticAbility
	{
		public RustTickAbility0(GameState state)
		{
			super(state, "You may choose not to untap Rust Tick during your untap step.");

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "You may choose not to untap Rust Tick during your untap step.", new UntapDuringControllersUntapStep(This.instance()));
			replacement.makeOptional(You.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class RustTickAbility1 extends ActivatedAbility
	{
		public RustTickAbility1(GameState state)
		{
			super(state, "(1), (T): Tap target artifact. It doesn't untap during its controller's untap step for as long as Rust Tick remains tapped.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(tap(target, "Tap target artifact."));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new UntapDuringControllersUntapStep(target)));

			this.addEffect(createFloatingEffect(Intersect.instance(ABILITY_SOURCE_OF_THIS, Untapped.instance()), "It doesn't untap during its controller's untap step for as long as Rust Tick remains tapped.", part));
		}
	}

	public RustTick(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// You may choose not to untap Rust Tick during your untap step.
		this.addAbility(new RustTickAbility0(state));

		// (1), (T): Tap target artifact. It doesn't untap during its
		// controller's untap step for as long as Rust Tick remains tapped.
		this.addAbility(new RustTickAbility1(state));
	}
}
